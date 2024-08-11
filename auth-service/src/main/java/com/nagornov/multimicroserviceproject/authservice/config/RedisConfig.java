package com.nagornov.multimicroserviceproject.authservice.config;

import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        try {
            RedisConnectionFactory conFactory = new LettuceConnectionFactory();
            CustomLogger.info("Successfully created RedisConnectionFactory");
            return conFactory;
        } catch (Exception e) {
            CustomLogger.error("Error creating RedisConnectionFactory: " + e.getMessage());
            return null;
        }
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        try {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory());
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            CustomLogger.info("Successfully created RedisTemplate");
            return template;
        } catch (Exception e) {
            CustomLogger.error("Error creating RedisTemplate: " + e.getMessage());
            return null;
        }
    }
}
