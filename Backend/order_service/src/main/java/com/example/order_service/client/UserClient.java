package com.example.order_service.client;

import com.example.order_service.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/api/addresses/user/{userId}/{addressId}")
    AddressResponse getAddress(@PathVariable("userId") Long userId, @PathVariable("addressId") Long addressId);
}
