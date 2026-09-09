package com.example.inventory_service.client;

import com.example.inventory_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{productId}")
    ProductResponse getProductById(@PathVariable("productId") Long productId);
}
