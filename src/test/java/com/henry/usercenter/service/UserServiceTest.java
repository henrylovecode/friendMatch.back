package com.henry.usercenter.service;
import java.util.Arrays;
import java.util.List;

import com.henry.usercenter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * FileName:     UserServiceTest
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2022-09-01
 * Description :
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();

        user.setUsername("testHenry");
        user.setUserAccount("123");
        user.setAvatarUrl("https://profile.csdnimg.cn/3/E/C/3_qq_22041375");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }


    @Test
    void uesrRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.uesrRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1 , result);

        userAccount="yu";

        result = userService.uesrRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1 , result);

        userAccount = "yupi";
        userPassword= "123456";
        result = userService.uesrRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1 ,result);

        userAccount ="yu pi";
        userPassword="12345678";
        result = userService.uesrRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1 ,result);


        checkPassword ="123456789";
        result = userService.uesrRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1 ,result);

        userAccount="testHenry";
        checkPassword="12345678";
        result = userService.uesrRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1 ,result);

        /*userAccount="yupi";
        result = userService.uesrRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result > 0) ;*/
    }
    @Test
    public  void searchUsersByTags(){
        List<String> tagName = Arrays.asList("java","python");
        List<User> userList = userService.searchUsersByTags(tagName);
        Assert.assertNotNull(userList);
    }
}