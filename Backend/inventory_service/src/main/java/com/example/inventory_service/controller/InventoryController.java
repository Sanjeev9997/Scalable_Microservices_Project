package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.dto.StockUpdateRequest;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody InventoryRequest inventoryRequest) {
       return ResponseEntity.ok(inventoryService.createInventory(inventoryRequest));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable Long productId) {
       return ResponseEntity.ok(inventoryService.getInventory(productId));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkStock(@RequestParam Long productId,@RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.isInStock(productId,quantity));
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveStock(@RequestBody StockUpdateRequest stockUpdateRequest) {
        inventoryService.reserveStock(stockUpdateRequest.getProductId(),stockUpdateRequest.getQuantity());
        return ResponseEntity.ok("Reserved");
    }
    @PostMapping("/release")
    public ResponseEntity<String> releaseStock(@RequestBody StockUpdateRequest stockUpdateRequest) {
        inventoryService.releaseStock(stockUpdateRequest.getProductId(),stockUpdateRequest.getQuantity());
        return ResponseEntity.ok("Released");
    }
    @PostMapping("/deduct")
    public ResponseEntity<String> deductStock(@RequestBody StockUpdateRequest stockUpdateRequest) {
        inventoryService.deductStock(stockUpdateRequest.getProductId(),stockUpdateRequest.getQuantity());
        return ResponseEntity.ok("Deducted");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStock(@RequestBody StockUpdateRequest stockUpdateRequest) {
        inventoryService.addStock(stockUpdateRequest.getProductId(),stockUpdateRequest.getQuantity());
        return ResponseEntity.ok("Added");
    }
}
