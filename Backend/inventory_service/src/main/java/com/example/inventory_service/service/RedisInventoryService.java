package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.entity.Inventory;
import com.example.inventory_service.repository.RedisInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.inventory_service.repository.RedisInventoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisInventoryService {

    private final RedisInventoryRepository redisInventoryRepository;
    private final InventoryService inventoryService;
    private static final Logger log= LoggerFactory.getLogger(RedisInventoryService.class);

    // Save available quantity in Redis
    public void saveStock(Long productId, Integer availableQuantity) {
        log.info("saveStock called");
        InventoryRequest request=new InventoryRequest();
        request.setProductId(productId);
        request.setQuantity(availableQuantity);
        log.info("creating inventory");
        inventoryService.createInventory(request);
        log.info("inventory created");
        redisInventoryRepository.saveStock(
                productId,
                availableQuantity
        );
        log.info("saveStock completed");
    }

    // Get available quantity from Redis
    public Long getStock(Long productId) {
        log.info("getStock called");

        log.info("getting inventory via redis");
        Long stock = redisInventoryRepository.getStock(productId);


        // Redis has the stock
        if (stock != null&&stock!=0) {
            log.info("Inventory fetched via redis");
            return stock;
        }
        log.info("Inventory not found in redis");
        // Redis doesn't have it → get from DB
        log.info("getting inventory via db");
        InventoryResponse inventory = inventoryService.getInventory(productId);

        if (inventory == null || inventory.getAvailableQuantity() == null) {
            log.info("Inventory not found in db");
            return null;
        }
        log.info("Inventory fetched via db");
        Integer dbStock = inventory.getAvailableQuantity();

        // Put DB value into Redis
        redisInventoryRepository.saveStock(
                productId,
                dbStock
        );
        log.info("getStock completed");
        return dbStock.longValue();
    }

    // Reserve stock
    public boolean reserveStock(
            Long productId,
            Integer quantity) {
        log.info("reserveStock called");

        log.info("Getting available quantity");
        Long remaining =
                redisInventoryRepository.decrementStock(
                        productId,
                        quantity
                );

        // Product doesn't exist in Redis
        if (remaining == null) {
            log.info("Inventory not found in redis");
            return false;
        }
        log.info("Available stock fetched");
        // Not enough stock
        if (remaining < 0) {
            log.info("Stock is not sufficient. Rollback to prev stock.");
            // Rollback

            redisInventoryRepository.incrementStock(
                    productId,
                    quantity
            );
            log.info("Rollback done.");
            return false;
        }

        log.info("reserveStock completed");
        return true;
    }
}