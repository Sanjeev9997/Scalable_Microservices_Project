package com.example.order_service.service;

import com.example.order_service.dto.KafkaTopics;
import com.example.order_service.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final Logger log= LoggerFactory.getLogger(OrderEventProducer.class);
    public void publishOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        log.info("Publishing order created event");
        kafkaTemplate.send(KafkaTopics.ORDER_CREATED, orderCreatedEvent.getOrderId().toString(), orderCreatedEvent);
        log.info("Order created event sent successfully");
    }

}
