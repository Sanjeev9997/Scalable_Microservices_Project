package com.example.order_service.service;

import com.example.order_service.dto.KafkaTopics;
import com.example.order_service.dto.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventConsumer {
    private final OrderService orderService;
    private static final Logger log= LoggerFactory.getLogger(PaymentEventConsumer.class);
    @KafkaListener(
            topics = KafkaTopics.PAYMENT_COMPLETED
    )
    public void listenPaymentCompleted(PaymentCompletedEvent paymentCompletedEvent) {
        System.out.println("Received PaymentCompleted event");
        log.info("Order recieved successfully");
        log.info("Order Id: "+paymentCompletedEvent.getOrderId());
        orderService.shipOrder(paymentCompletedEvent);
        log.info("Order ship request sent");
    }
}
