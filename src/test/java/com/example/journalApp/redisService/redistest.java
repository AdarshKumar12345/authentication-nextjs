package com.example.journalApp.redisService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class redistest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisCheck(){
        redisTemplate.opsForValue().set("good" , "bad");
        Object  s = redisTemplate.opsForValue().get("good");
        String value = s.toString();

        int a = 1;


    }

}
