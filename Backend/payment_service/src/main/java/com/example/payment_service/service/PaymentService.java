package com.example.payment_service.service;

import com.example.payment_service.dto.OrderCreatedEvent;
import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {
    public PaymentResponse makePayment(OrderCreatedEvent event);
    public List<PaymentResponse> getPaymentDetails(Long orderId);
}
