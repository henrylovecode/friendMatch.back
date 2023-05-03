package com.henry.usercenter.service;

import com.henry.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.util.StopWatch;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * FileName:     InsertUsersTest
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-15
 * Description :
 */
@SpringBootTest
public class InsertUsersTest {

    @Resource
    private UserService userService;

    private ExecutorService executorService = new ThreadPoolExecutor(60,1000,10000, TimeUnit.MINUTES,new ArrayBlockingQueue<>(10000));

    /**
     * 批量删除用户
     */
    //  @Scheduled(fixedDelay = 5000)

    @Test
    public void doInsertUser(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM=100000;
        List<User> userList = new ArrayList<>();
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
            userList.add(user);
        }
        userService.saveBatch(userList , 100);
        stopWatch.stop();
        System.out.println(  stopWatch.getTotalTimeMillis());


    }

    @Test
    public void doConcurrencyInsertUser(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM=10000;

        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<User> userList = new ArrayList<>();
            while (true){
                j++;
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
                userList.add(user);
                if (j%10000 == 0){
                    break;
                }
            }
           CompletableFuture<Void> future =  CompletableFuture.runAsync(()->{
                userService.saveBatch(userList , 10000);
            });
            futureList.add(future);

        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(  stopWatch.getTotalTimeMillis());

    }
}
