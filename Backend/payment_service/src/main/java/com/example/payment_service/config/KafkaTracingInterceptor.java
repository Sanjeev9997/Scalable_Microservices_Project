package com.example.payment_service.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapPropagator;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.listener.RecordInterceptor;

public class KafkaTracingInterceptor
       {
//      implements RecordInterceptor<String, Object>
//           private final TextMapPropagator propagator;
//
//    public KafkaTracingInterceptor(OpenTelemetry openTelemetry) {
//        this.propagator =
//                openTelemetry
//                        .getPropagators()
//                        .getTextMapPropagator();
//    }
//
//    private static final TextMapGetter<ConsumerRecord<String, Object>> GETTER =
//            new TextMapGetter<>() {
//
//                @Override
//                public Iterable<String> keys(
//                        ConsumerRecord<String, Object> carrier) {
//
//                    return carrier.headers()
//                            .toArray()
//                            .length > 0
//                            ? carrier.headers()
//                            .toArray()
//                            .length > 0
//                            ? java.util.stream.StreamSupport
//                            .stream(
//                                    carrier.headers()
//                                            .headers("traceparent")
//                                            .spliterator(),
//                                    false
//                            )
//                            .map(h -> "traceparent")
//                            .toList()
//                            : java.util.List.of()
//                            : java.util.List.of();
//                }
//
//                @Override
//                public String get(
//                        ConsumerRecord<String, Object> carrier,
//                        String key) {
//
//                    var header =
//                            carrier.headers().lastHeader(key);
//
//                    if (header == null) {
//                        return null;
//                    }
//
//                    return new String(
//                            header.value(),
//                            java.nio.charset.StandardCharsets.UTF_8
//                    );
//                }
//            };
//
//    @Override
//    public ConsumerRecord<String, Object> intercept(
//            ConsumerRecord<String, Object> record,
//            Consumer<String, Object> consumer) {
//
//        Context parentContext =
//                propagator.extract(
//                        Context.current(),
//                        record,
//                        GETTER
//                );
//
//        SpanContext spanContext =
//                Span.fromContext(parentContext)
//                        .getSpanContext();
//
//        if (spanContext.isValid()) {
//
//            MDC.put(
//                    "traceId",
//                    spanContext.getTraceId()
//            );
//
//            MDC.put(
//                    "spanId",
//                    spanContext.getSpanId()
//            );
//        }
//
//        return record;
//    }
//
//    @Override
//    public void success(
//            ConsumerRecord<String, Object> record,
//            Consumer<String, Object> consumer) {
//
//        MDC.remove("traceId");
//        MDC.remove("spanId");
//    }
//
//    @Override
//    public void failure(
//            ConsumerRecord<String, Object> record,
//            Exception exception,
//            Consumer<String, Object> consumer) {
//
//        MDC.remove("traceId");
//        MDC.remove("spanId");
//    }
}