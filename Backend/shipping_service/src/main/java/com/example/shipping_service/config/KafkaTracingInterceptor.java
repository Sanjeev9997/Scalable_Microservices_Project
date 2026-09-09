package com.example.shipping_service.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jspecify.annotations.Nullable;
import org.slf4j.MDC;
import org.springframework.kafka.listener.RecordInterceptor;

public class KafkaTracingInterceptor implements RecordInterceptor<String,Object> {
    @Override
    public @Nullable ConsumerRecord<String, Object> intercept(ConsumerRecord<String, Object> record, Consumer<String, Object> consumer) {

        SpanContext context= Span.current().getSpanContext();

        if (context.isValid()){
            MDC.put("traceId",context.getTraceId());
            MDC.put("spanId",context.getSpanId());
        }
        return  record;
    }
}
