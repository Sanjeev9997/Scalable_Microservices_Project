package com.example.gateway_server.security;



import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {
    private static  final List<String> PUBLIC_ENDPOINTS = List.of(
            "/auth-service/auth/login",
            "/auth-service/auth/register",
            "/auth-service/auth/refresh",
            "/eureka-server",
            "/product-service/products",
            "/inventory-service/stock"
    );
    public boolean isSecured(ServerHttpRequest request) {
        return PUBLIC_ENDPOINTS
                .stream()
                .noneMatch(uri ->
                        request.getURI()
                                .getPath()
                                .startsWith(uri));
    }
}
