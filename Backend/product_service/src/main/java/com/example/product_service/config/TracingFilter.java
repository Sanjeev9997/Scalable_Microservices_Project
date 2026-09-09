package com.example.product_service.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TracingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        SpanContext spanContext = Span.current().getSpanContext();

        try {
            if (spanContext.isValid()) {
                MDC.put("traceId", spanContext.getTraceId());
                MDC.put("spanId", spanContext.getSpanId());
            }

            filterChain.doFilter(request, response);

        } finally {
            MDC.remove("traceId");
            MDC.remove("spanId");
        }
    }
}