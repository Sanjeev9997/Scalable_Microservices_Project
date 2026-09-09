package com.example.Cart_Service.service.Impl;

import com.example.Cart_Service.client.ProductClient;
import com.example.Cart_Service.dto.ProductResponse;
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
    public ProductResponse getProductById(Long id){
        return  productClient.getProductById(id);
    }

    private  ProductResponse productFallback(Long id,Throwable throwable){
        throw new RuntimeException("Product service is down temporarily");
    }
}
