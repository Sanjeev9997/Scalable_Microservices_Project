package com.example.payment_service.dto;

import com.example.payment_service.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;

    private Long userId;

    private Double amount;

    private PaymentMethod paymentMethod;
}