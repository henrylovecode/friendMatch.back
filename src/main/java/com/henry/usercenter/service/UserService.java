package com.henry.usercenter.service;

import com.henry.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.usercenter.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author lenovo
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2022-09-01 16:55:53
*/
public interface UserService extends IService<User> {



    /**
     * 用户注释
     * @param userAccount 账户
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @Param planetCode 星球编号
     * @return 新用户id
     */
    long uesrRegister(String userAccount  , String userPassword , String checkPassword,String planetCode);


    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount , String userPassword , HttpServletRequest request);


    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */

    User getSafetyUser(User originUser);


    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     * @param tagNameList
     * @return
     */
    List<User>  searchUsersByTags(List<String> tagNameList);


    /**
     *
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUser(User user,User loginUser);

    /**
     * 获取当前登录用户信息
     * @return
     */
    User getLoginUser(HttpServletRequest request);


    /**
     * 是否为管理员
     * @param request
     * @return
     */
    boolean isAdmin( HttpServletRequest request);

    /**
     * 是否为管理员
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);

    /**
     *匹配用户
     * @param num
     * @param loginUser
     * @return
     */
    List<User> matchUsers(Long num, User loginUser);
}
