package com.example.payment_service.dto;

import com.example.payment_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent {

    private Long orderId;

    private PaymentStatus paymentStatus;
}

