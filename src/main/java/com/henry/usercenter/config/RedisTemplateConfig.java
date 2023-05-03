package com.henry.usercenter.config;

import org.apache.commons.math3.analysis.function.StepFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * FileName:     RedisTemplateConfig
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-16
 * Description :
 */
@Configuration
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<String , Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String , Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        return redisTemplate;
    }

}
