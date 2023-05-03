package com.henry.usercenter.once;
import java.util.Date;

import com.henry.usercenter.mapper.UserMapper;
import com.henry.usercenter.model.domain.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

/**
 * FileName:     InsertUsers
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-15
 * Description :
 */
@Component
public class InsertUsers {
    @Resource
    private UserMapper userMapper;

    /**
     * 批量删除用户
     */
  //  @Scheduled(fixedDelay = 5000)
    public void doInsertUser(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM=1000;
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("假henry");
            user.setUserAccount("fakeHenry");
            user.setAvatarUrl("https://www.logosc.cn/uploads/resources/2018/11/24/1543048958.jpg");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("15978549854");
            user.setTags("[]");
            user.setUserStatus(0);
            user.setEmail("");
            user.setUserRole(0);
            user.setPlanetCode("11111111");
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(  stopWatch.getTotalTimeMillis());


    }
}
