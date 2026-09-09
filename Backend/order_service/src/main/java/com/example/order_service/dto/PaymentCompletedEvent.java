package com.example.order_service.dto;

import com.example.order_service.enums.PaymentStatus;
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
