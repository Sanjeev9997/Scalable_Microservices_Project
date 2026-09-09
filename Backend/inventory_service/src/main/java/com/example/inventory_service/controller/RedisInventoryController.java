package com.example.inventory_service.controller;

import com.example.inventory_service.service.RedisInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redis/inventory")
public class RedisInventoryController {

    private final RedisInventoryService redisInventoryService;

    @PostMapping("/stock/{productId}")
    public ResponseEntity<String> saveStock(@PathVariable Long productId, @RequestParam Integer quantity){
        redisInventoryService.saveStock(productId,quantity);

        return new ResponseEntity<>("Stock saved in Redis", HttpStatus.CREATED);
    }

    @GetMapping("/stock/{productId}")
    public ResponseEntity<Long> getStock(@PathVariable Long productId){
        return new ResponseEntity<>(redisInventoryService.getStock(productId), HttpStatus.OK);
    }

    @PostMapping("/stock/reserve/{productId}")
    public ResponseEntity<String> reserveStock(@PathVariable Long productId,@RequestParam Integer quantity){
       boolean reserved= redisInventoryService.reserveStock(productId,quantity);
       if(!reserved){
           return new ResponseEntity<>("Insufficient stock",HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>("Stock reserved",HttpStatus.OK);
    }

}
