package com.example.payment_service.service;

import com.example.payment_service.dto.KafkaTopics;
import com.example.payment_service.dto.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {
    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;
    private static final Logger log= LoggerFactory.getLogger(PaymentEventProducer.class);

    public void publish(PaymentCompletedEvent paymentCompletedEvent) {
        log.info("Sending payment completed event");
        kafkaTemplate.send(KafkaTopics.PAYMENT_COMPLETED,paymentCompletedEvent.getOrderId().toString(), paymentCompletedEvent);
        log.info("Payment Completed");
    }
}
