package com.example.inventory_service.service;

import com.example.inventory_service.client.ProductClient;
import com.example.inventory_service.dto.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class ProductClientService {
    private final ProductClient productClient;

    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "productFallback"
    )
    public ProductResponse getProductById( Long productId){
        return  productClient.getProductById(productId);
    }

    private  ProductResponse productFallback(Long productId,Throwable throwable){
        throw new RuntimeException("Product service is unavailable right now.");
    }
}
