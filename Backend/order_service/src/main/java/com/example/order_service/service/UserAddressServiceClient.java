package com.example.order_service.service;

import com.example.order_service.client.UserClient;
import com.example.order_service.dto.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAddressServiceClient {
    private final UserClient userClient;

    @CircuitBreaker(
            name = "userService",
            fallbackMethod = "addressFallback"
    )
    public AddressResponse getAddress(Long userId,Long addressId){
        return  userClient.getAddress(userId,addressId);
    }

    private  AddressResponse addressFallback(Long userId,Long addressId,

                                             Throwable throwable){

        throw new RuntimeException("User service is unavailable right now.");

    }

}
