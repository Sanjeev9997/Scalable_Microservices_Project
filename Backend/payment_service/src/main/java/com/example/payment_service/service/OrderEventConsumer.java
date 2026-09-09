package com.example.payment_service.service;

import com.example.payment_service.dto.KafkaTopics;
import com.example.payment_service.dto.OrderCreatedEvent;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final PaymentService paymentService;

    private static final Logger log =
            LoggerFactory.getLogger(OrderEventConsumer.class);

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED)
    public void consume(
            ConsumerRecord<String, OrderCreatedEvent> record) {
        SpanContext context = Span.current().getSpanContext();

        log.info(
                "Kafka trace valid={} traceId={} spanId={}",
                context.isValid(),
                context.getTraceId(),
                context.getSpanId()
        );

        log.info("Received Order Created Event");
        log.info("Sending request for payment process");

        paymentService.makePayment(record.value());

        log.info("Payment Process Completed");
    }
}