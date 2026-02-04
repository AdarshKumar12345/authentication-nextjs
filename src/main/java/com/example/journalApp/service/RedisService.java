package com.example.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;




    public <T> T get(String key, Class<T> entitnyClass) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            if(value == null) {
                return null;
            }
            return mapper.readValue(value.toString(), entitnyClass);
        } catch (Exception e) {
            log.error("Redis Get Error: " + e.getMessage());
            return null;
        }
    }
    public void set(String key, Object value, long timeoutInSeconds) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String JsonVlue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, JsonVlue,timeoutInSeconds , TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis Set Error: " + e.getMessage());
        }
    }
}
