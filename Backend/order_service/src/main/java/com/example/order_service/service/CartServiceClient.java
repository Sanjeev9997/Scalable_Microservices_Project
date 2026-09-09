package com.example.order_service.service;


import com.example.order_service.client.CartClient;
import com.example.order_service.dto.CartResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceClient {


    private final CartClient cartClient;

    @CircuitBreaker(
            name = "cartService",
            fallbackMethod = "cartFallback"
    )
    public CartResponse getCart(Long userId) {
        return cartClient.getCart(userId);
    }

    private CartResponse cartFallback(Long userId,Throwable throwable) {
        throw new RuntimeException(new RuntimeException("Cart service is temporarily unavailable"));
    }
}
