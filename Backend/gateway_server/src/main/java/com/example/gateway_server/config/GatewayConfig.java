package com.example.gateway_server.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

//    @Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("product-service",
//                        r -> r.path("/products/**")
//                                .uri("lb://PRODUCT-SERVICE"))
//
//                .route("inventory-service",
//                        r->r.path("/api/inventory/**")
//                                 .uri("lb://INVENTORY-SERVICE"))
//
//                .route("order-service",r->r.path("/order/**")
//                                 .uri("lb://ORDER-SERVICE"))
//
//
//                .build();
//    }
}
