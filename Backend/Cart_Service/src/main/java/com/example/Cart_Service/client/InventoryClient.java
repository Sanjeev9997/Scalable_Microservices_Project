package com.example.Cart_Service.client;

import com.example.Cart_Service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/{productId}")
    InventoryResponse getInventory(@PathVariable("productId") Long productId);

    @GetMapping("/api/inventory/check")
    Boolean checkStock(@RequestParam("productId") Long productId,
                       @RequestParam("quantity") Integer quantity);


}
