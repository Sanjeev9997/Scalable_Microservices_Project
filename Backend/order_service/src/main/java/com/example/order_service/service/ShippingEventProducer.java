package com.example.order_service.service;

import com.example.order_service.dto.KafkaTopics;
import com.example.order_service.dto.ShippingCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingEventProducer {
    private final Logger logger= LoggerFactory.getLogger(ShippingEventProducer.class);
    private final KafkaTemplate<String, ShippingCreatedEvent> kafkaTemplate;
    public void sendShippingCreatedEvent(ShippingCreatedEvent shippingCreatedEvent) {
        logger.info("Sending Shipping Created event to Kafka");
        kafkaTemplate.send(KafkaTopics.SHIPPING_CREATED,shippingCreatedEvent.getOrderId().toString(), shippingCreatedEvent);
        logger.info("Shipping Created event sent to Kafka");
    }
}
