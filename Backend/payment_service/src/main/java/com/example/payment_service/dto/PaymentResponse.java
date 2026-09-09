package com.example.payment_service.dto;

import com.example.payment_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private long paymentId;
    private String transactionId;
    private PaymentStatus paymentStatus;

}
