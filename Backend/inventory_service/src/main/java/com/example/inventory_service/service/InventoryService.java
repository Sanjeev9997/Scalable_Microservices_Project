package com.example.inventory_service.service;


import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest inventoryRequest);
    InventoryResponse getInventory(Long productId);
    boolean isInStock(Long productId,Integer quantity);
    void reserveStock(Long productId,Integer quantity);
    void releaseStock(Long productId,Integer quantity);
    void deductStock(Long productId,Integer quantity);
    void addStock(Long productId,Integer quantity);

}
