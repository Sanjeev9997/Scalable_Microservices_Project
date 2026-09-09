package com.example.order_service.dto;

import com.example.order_service.enums.PaymentMethod;
import lombok.Data;



@Data
public class CreateOrderRequest {
    private Long userId;
    private Long addressId;
    private PaymentMethod paymentMethod;
}

