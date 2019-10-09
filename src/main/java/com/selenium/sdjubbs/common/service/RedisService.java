package com.selenium.sdjubbs.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通过StringRedisTemplate对象来操作redis
 */
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional(rollbackFor = {Exception.class})
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Transactional(readOnly = true)
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Boolean delete(String key){return stringRedisTemplate.delete(key);}

}