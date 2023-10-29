package com.lezhin.t.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisLockRepository {

    private final static String LOCK = "lock";
    private RedisTemplate<String, String> redisTemplate;

    public RedisLockRepository(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean lock(final String key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(LOCK + key, LOCK, Duration.ofMillis(3_000));
    }

    public Boolean unlock(final String key) {
        return redisTemplate.delete(LOCK + key);
    }
}
