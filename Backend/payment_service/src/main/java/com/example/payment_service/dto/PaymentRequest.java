package com.example.payment_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long orderId;
    private Double amount;
    private String paymentMethod;

}
