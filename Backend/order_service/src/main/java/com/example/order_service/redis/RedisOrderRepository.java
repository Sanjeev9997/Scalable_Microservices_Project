package com.example.order_service.redis;

import com.example.order_service.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisOrderRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "order:";

    private static final Duration TTL =
            Duration.ofMinutes(30);

    public void save(OrderResponse order) {

        String key = KEY_PREFIX + order.getOrderId();

        redisTemplate.opsForValue().set(
                key,
                order,
                TTL
        );
    }

    public OrderResponse get(Long orderId) {

        Object value = redisTemplate.opsForValue()
                .get(KEY_PREFIX + orderId);

        if (value == null) {
            return null;
        }

        return (OrderResponse) value;
    }
    public void delete(Long orderId) {

        redisTemplate.delete(
                KEY_PREFIX + orderId
        );
    }

}
