package com.example.order_service.dto;

import lombok.Data;

@Data
public class ShippingRequest {
    private String orderId;
    private String customerName;
    private String shippingAddress;
    private String courierPartner;
}
