package com.example.lab3_behind.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private static RedisTemplate<String,String> redisTemplate;

    public static void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }
    public static void set(String key,String value,Long timeout,TimeUnit unit){
        redisTemplate.opsForValue().set(key,value,timeout,unit);
    }
    public static String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public static void delete(String key){
        redisTemplate.delete(key);
    }
    public static boolean hasKey(String key) {
        try {
            redisTemplate.hasKey(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
