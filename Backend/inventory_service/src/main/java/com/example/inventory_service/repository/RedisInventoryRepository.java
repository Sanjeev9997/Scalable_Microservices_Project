package com.example.inventory_service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisInventoryRepository {
    private final StringRedisTemplate redisTemplate;

    public void saveStock(Long productId,int quantity){
        redisTemplate.opsForValue().set("stock:"+productId,String.valueOf(quantity));
    }


    public Long getStock(Long productId){
        String value=redisTemplate.opsForValue().get("stock:"+productId);

        return value==null?0L:Long.parseLong(value);
    }

    public Long decrementStock(Long productId,int quantity){
        return redisTemplate.opsForValue().decrement("stock:"+productId,quantity);

    }
    public Long incrementStock(Long productId,int quantity){
        return redisTemplate.opsForValue().increment("stock:"+productId,quantity);
    }
}
