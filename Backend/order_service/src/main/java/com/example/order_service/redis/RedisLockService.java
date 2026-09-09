package com.example.order_service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisLockService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PREFIX =
            "inventory:lock:";

    private static final Duration LOCK_TTL =
            Duration.ofSeconds(10);

    public String acquireLock(Long productId){
       String lockKey=PREFIX+productId;
        String lockValue =
                UUID.randomUUID().toString();

        Boolean acquired=redisTemplate.opsForValue().setIfAbsent(lockKey,lockValue,LOCK_TTL);

        if(Boolean.TRUE.equals(acquired)){
            return lockValue;
        }
        return null;
    }
    public void releaseLock(Long productId){
        String lockKey=PREFIX+productId;

        redisTemplate.delete(lockKey);
    }
}
