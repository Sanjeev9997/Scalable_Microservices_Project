package com.example.order_service.service;

import com.example.order_service.dto.NotificationRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationEventProducer {

    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;
    private static final Logger log= LoggerFactory.getLogger(NotificationEventProducer.class);
    public void sendNotificationEvent(NotificationRequest notificationRequest) {
        log.info("Sending notification event");
        kafkaTemplate.send("notification-event",notificationRequest.getOrderId().toString(),notificationRequest);
        log.info("Notification event sent successfully");
    }
}
