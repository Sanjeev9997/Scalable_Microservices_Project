package com.example.Cart_Service.service.Impl;

import com.example.Cart_Service.client.InventoryClient;
import com.example.Cart_Service.dto.InventoryResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class InventoryClientService {
    private final InventoryClient inventoryClient;


    @CircuitBreaker(
            name = "inventoryService",
            fallbackMethod = "getInventoryFallback"
    )
    public InventoryResponse getInventory(Long productId){
       return inventoryClient.getInventory(productId);
    }
    private  InventoryResponse getInventoryFallback(Long productId,Throwable throwable){
        throw new RuntimeException("Inventory service is down now.");
    }
    @CircuitBreaker(
            name = "inventoryService",
            fallbackMethod = "checkStockFallback"
    )
    public Boolean checkStock( Long productId,
                        Integer quantity){
        return inventoryClient.checkStock(productId, quantity);
    }

    private Boolean checkStockFallback(Long productId,Throwable throwable){
        throw new RuntimeException("Inventory service is down now.");
    }
}
