package com.henry.usercenter.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henry.usercenter.mapper.UserMapper;
import com.henry.usercenter.model.domain.User;
import com.henry.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * FileName:     PreCacheJob
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-16
 * Description :
 */
@Component
@Slf4j
public class PreCacheJob {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String , Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    private List<Long> mainUserList = Arrays.asList(1L);

    //每天执行，预热推荐用户
    @Scheduled(cron = "0 5 20 * * *")
    public void doCacheRecommendUser(){

        RLock lock = redissonClient.getLock("yupao:precachejob:docache:lock");
        try {
            if (lock.tryLock(0 , 30000L , TimeUnit.MILLISECONDS)){
                System.out.println("getLock" + Thread.currentThread().getId());
                for (Long userId : mainUserList){
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1,20)  , queryWrapper);
                    String redisKey = String.format("yupao:user:recommend:%s",userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    try {
                        valueOperations.set(redisKey, userPage,300000 , TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("redis set key error" , e);
                    }
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock.isHeldByCurrentThread()){
                System.out.println("unlock"+ Thread.currentThread().getId());
                lock.unlock();
            }
        }


    }



}
