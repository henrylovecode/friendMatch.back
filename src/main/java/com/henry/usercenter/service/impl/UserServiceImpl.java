package com.henry.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.henry.usercenter.common.ErrorCode;
import com.henry.usercenter.constant.userConstant;
import com.henry.usercenter.exception.BusinessException;
import com.henry.usercenter.model.domain.User;
import com.henry.usercenter.model.vo.UserVO;
import com.henry.usercenter.service.UserService;
import com.henry.usercenter.mapper.UserMapper;
import com.henry.usercenter.utils.AlgorithmUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.method.MethodDescription;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.henry.usercenter.constant.userConstant.ADMIN_ROLE;
import static com.henry.usercenter.constant.userConstant.USER_LOGIN_STATE;

/**
* @author lenovo
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-09-01 16:55:53
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private  UserMapper userMapper;

    /**
     * 盐值：混淆密码
     */
     private static final  String SALT  = "yupi";




    @Override
    public long uesrRegister(String userAccount, String userPassword, String checkPassword , String planetCode) {

        if(StringUtils.isAnyBlank(userAccount , userPassword , checkPassword , planetCode)){
            //todo  修改自定义异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户过短");
        }

        if(userPassword.length() < 8 || checkPassword.length() < 8 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }

        if(planetCode.length() > 5 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长" );
        }

        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return  -1;
        }

        if(!userPassword.equals(checkPassword)){
            return -1;
        }

        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount" , userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count > 0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
        }

        //星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode" , planetCode);
         count = userMapper.selectCount(queryWrapper);
        if(count > 0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编号重复");
        }

        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());


        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }
        return  user.getId();



    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAnyBlank(userAccount , userPassword )){
            return  null;
        }

        if(userAccount.length() < 4){
            return  null;
        }

        if(userPassword.length() < 8  ){
            return null;
        }



        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return  null;
        }


        //加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount" , userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if(user == null){
            log.info("user failed , userAccount cannot match userPassword");
            return null;
        }


        //用户脱敏
        User safetyUser = getSafetyUser(user);
        //3.记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE ,safetyUser);

        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser){
        if(originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setTags(originUser.getTags());
        return safetyUser;

    }


    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {


        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;

    }

    /**
     * 根据标签搜索用户
     * @Param  tagNameList 用户要拥有的标签
     * @return
     */
    @Override
    public List<User> searchUsersByTags(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 1. 先查询所有用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);
        Gson gson = new Gson();
        // 2. 在内存中判断是否包含要求的标签
        return userList.stream().filter(user -> {
            String tagsStr = user.getTags();
            Set<String> tempTagNameSet = gson.fromJson(tagsStr, new TypeToken<Set<String>>() {
            }.getType());
            tempTagNameSet = Optional.ofNullable(tempTagNameSet).orElse(new HashSet<>());
            for (String tagName : tagNameList) {
                if (!tempTagNameSet.contains(tagName)) {
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public int updateUser(User user, User loginUser) {
        long userId = user.getId();
        if (userId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //如果是管理员，允许更新任意用户
        //如果不是管理员，允许更新自己
        if (  !isAdmin(loginUser) &&  userId != loginUser.getId()){
            throw  new BusinessException(ErrorCode.NO_AUTO);
        }
        User oldUser = userMapper.selectById(userId);
        if (oldUser == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return userMapper.updateById(user);

    }


    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NO_AUTO);
        }
        return (User) userObj;
    }


    /**
     * 是否为管理员
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin( HttpServletRequest request){
        //管理员查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null &&  user.getUserRole()  == userConstant.ADMIN_ROLE;

    }

    /**
     * 是否为管理员
     * @param  loginUser
     * @return
     */
    @Override
    public boolean isAdmin(User loginUser){
        //管理员查询
        return loginUser != null &&  loginUser.getUserRole()  == userConstant.ADMIN_ROLE;
    }

    @Override
    public List<User> matchUsers(Long num, User loginUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id" , "tags");
        queryWrapper.isNotNull("tags");
        List<User> userList = this.list(queryWrapper);
        String tags = loginUser.getTags();
        Gson gson = new Gson();
        List<String> tagList = gson.fromJson(tags , new  TypeToken<List<String>>(){
        }.getType());

        //用户列表的小标 => 相识度
        List<Pair<User , Long>> list = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            String userTags = user.getTags();
            //无标签或者为当前自己
            if (StringUtils.isBlank(userTags) || user.getId() == loginUser.getId()){
                continue;
            }
            List<String> userTagList = gson.fromJson(userTags,new TypeToken<List<String>>(){
            }.getType());
            //计算分数
            long distance = AlgorithmUtils.minDistance(tagList, userTagList);
           list.add(new Pair<>(user,  distance));
        }
        List<Pair<User , Long>> topUserList = list.stream()
                                .sorted((a,b) -> (int) (a.getValue() - b.getValue()))
                                .limit(num)
                                .collect(Collectors.toList());
        //原本顺序的userId列表
        List<Long> userIdList = topUserList.stream().map(pair -> pair.getKey().getId()).collect(Collectors.toList());
        QueryWrapper<User> userQueryWrapper  =  new QueryWrapper<>();
        userQueryWrapper.in("id" , userIdList);
        //1 , 3 , 2

        Map<Long, List<User>> userIdUserListMap = this.list(userQueryWrapper)
                                    .stream()
                                    .map(user -> getSafetyUser(user))
                                    .collect(Collectors.groupingBy(User::getId));

        List<User> finalUserList = new ArrayList<>();
        for (Long userId : userIdList) {
            finalUserList.add(userIdUserListMap.get(userId).get(0));
        }

        return finalUserList;

    }

}




