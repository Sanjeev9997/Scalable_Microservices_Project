package com.example.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "shipping-service")
public interface ShipingClient {



}
