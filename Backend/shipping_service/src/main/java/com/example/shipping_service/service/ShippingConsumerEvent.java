package com.example.shipping_service.service;

import com.example.shipping_service.dto.KafkaTopics;
import com.example.shipping_service.dto.ShipmentRequest;
import com.example.shipping_service.dto.ShippingCreatedEvent;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingConsumerEvent {
    private final ShippingService shippingService;
    private static final Logger log= LoggerFactory.getLogger(ShippingConsumerEvent.class);
    @KafkaListener(
            topics = KafkaTopics.SHIPPING_CREATED
    )
    public void shippingCreated(ShippingCreatedEvent shippingCreatedEvent) {
        SpanContext context = Span.current().getSpanContext();
        log.info(
                "HTTP TRACE valid={} traceId={} spanId={}",
                context.isValid(),
                context.getTraceId(),
                context.getSpanId()
        );
        log.info("Received Shipping Created Event");
        ShipmentRequest req=new  ShipmentRequest();
        req.setCustomerName(shippingCreatedEvent.getCustomerName());
        req.setShippingAddress(shippingCreatedEvent.getShippingAddress());
        req.setOrderId(shippingCreatedEvent.getOrderId());
        req.setCourierPartner(shippingCreatedEvent.getCourierPartner());
        log.info("Creating shipment");
        shippingService.createShipment(req);
        log.info("Shipment created");
    }
}
