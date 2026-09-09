package com.example.payment_service.service;

import com.example.payment_service.dto.OrderCreatedEvent;
import com.example.payment_service.dto.PaymentCompletedEvent;
import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.PaymentResponse;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.enums.PaymentStatus;
import com.example.payment_service.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;
    private static final Logger log= LoggerFactory.getLogger(PaymentServiceImpl.class);
    @Override
    public PaymentResponse makePayment(OrderCreatedEvent paymentRequest) {
        log.info("makePayment called");
        Payment payment=new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentDate(LocalDateTime.now());

        log.info("Saving payment details in db");
        Payment saved=paymentRepository.save(payment);
        log.info("Payment saved in db");
        // produce event
        log.info("Sending payment completed event");
        PaymentCompletedEvent paymentCompletedEvent=new PaymentCompletedEvent(paymentRequest.getOrderId(), PaymentStatus.SUCCESS);
        paymentEventProducer.publish(paymentCompletedEvent);
        log.info("Payment completed event sent");

        log.info("makePayment completed");
        return mapToPaymentResponse(saved);
    }

    @Override
    public List<PaymentResponse> getPaymentDetails(Long orderId) {
        log.info("getPaymentDetails called");

        log.info("Getting payment details from db");
        List<Payment> payments=paymentRepository.findByOrderId(orderId);
        log.info("Details fetched");
        List<PaymentResponse> responses=new ArrayList<>();
        for(Payment saved:payments){
            responses.add(mapToPaymentResponse(saved));
        }
        log.info("getPaymentDetails completed");
        return responses;
       }

       private PaymentResponse mapToPaymentResponse(Payment payment){
          return PaymentResponse.builder()
                  .paymentId(payment.getPaymentId())
                  .paymentStatus(payment.getPaymentStatus())
                  .transactionId(payment.getTransactionId())
                  .build();
       }
    }


