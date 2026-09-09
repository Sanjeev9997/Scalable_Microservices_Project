package com.example.notification_service.kafka;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.service.NotificationService;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private static final Logger log= LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationService notificationService;
    @KafkaListener(
            topics = "notification-topic"
    )
    public void consume(NotificationRequest request) {
        SpanContext context = Span.current().getSpanContext();

        log.info(
                "Kafka trace valid={} traceId={} spanId={}",
                context.isValid(),
                context.getTraceId(),
                context.getSpanId()
        );

        log.info("Received Event : " + request);
        notificationService.sendEmail(request);
        log.info("Send Email Success");
    }
}
