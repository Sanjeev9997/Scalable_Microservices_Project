package com.example.order_service.config;

import feign.RequestInterceptor;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignTracingConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->{
            TextMapPropagator propagator= GlobalOpenTelemetry.getPropagators().getTextMapPropagator();
            propagator.inject(Context.current(),requestTemplate
                    ,(carrier,key,value)->carrier.header(key,value));
        };
    }
}
