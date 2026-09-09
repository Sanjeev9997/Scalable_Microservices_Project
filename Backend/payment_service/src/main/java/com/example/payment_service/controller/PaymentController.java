package com.example.payment_service.controller;

import com.example.payment_service.dto.OrderCreatedEvent;
import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.PaymentResponse;
import com.example.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody OrderCreatedEvent paymentRequest) {
        return ResponseEntity.ok(paymentService.makePayment(paymentRequest));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentDetails(orderId));
    }

}
