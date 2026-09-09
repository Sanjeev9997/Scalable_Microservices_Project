package com.example.order_service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX =
            "order:idempotency:";

    private static final Duration TTL =
            Duration.ofHours(24);

    public boolean tryAcquire(String idempotencyKey) {

        Boolean acquired =
                redisTemplate.opsForValue().setIfAbsent(
                        PREFIX + idempotencyKey,
                        "PROCESSING",
                        TTL
                );

        return Boolean.TRUE.equals(acquired);
    }

    public Object get(String idempotencyKey) {

        return redisTemplate.opsForValue()
                .get(PREFIX + idempotencyKey);
    }

    public void markCompleted(
            String idempotencyKey,
            Long orderId) {

        redisTemplate.opsForValue().set(
                PREFIX + idempotencyKey,
                orderId,
                TTL
        );
    }

    public void delete(String idempotencyKey) {

        redisTemplate.delete(
                PREFIX + idempotencyKey
        );
    }
}