package com.henry.usercenter.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * FileName:     RedissonConfig
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-17
 * Description :
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private String host;

    private String port;

    @Bean
    public RedissonClient redissonClient(){
        // 1. Create config object
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s",host, port);
        config.useSingleServer().setAddress(redisAddress).setDatabase(3);


        // 2. Create Redisson instance
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        return  redisson;
    }


}
