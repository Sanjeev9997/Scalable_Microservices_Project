package com.example.gateway_server.security;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    private final Logger log= LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final RouteValidator routeValidator;
    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        log.info("Gateway filter started");

        // Allow CORS preflight requests
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            log.info("CORS preflight request - skipping JWT validation");
            return chain.filter(exchange);
        }

        // Public endpoint -> JWT check skip
        log.info("Public endpoints checking");

        if (!routeValidator.isSecured(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        log.info("Public endpoints validated");

        // Get Authorization header
        log.info("Getting authorization header");

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        log.info("Checking jwt is missing or not.");
        // JWT missing
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        // Extract JWT
        log.info("Extracting jwt token.");
        String token = authHeader.substring(7);

        // Validate JWT
        log.info("Token is"+token);
        log.info("Validating token");
        if (!jwtService.validateToken(token)) {
            return unauthorized(exchange);
        }

        // Extract claims
        log.info("Extracting claims");
        Claims claims = jwtService.extractClaims(token);

        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        // Remove client-provided headers and add verified values
        log.info("Remove client provided headers and add verified values");
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .headers(headers -> {
                    headers.remove("X-User-Email");
                    headers.remove("X-User-Role");

                    headers.set("X-User-Email", email);
                    headers.set("X-User-Role", role);
                })
                .build();

        return chain.filter(
                exchange.mutate()
                        .request(request)
                        .build()
        );
    }
    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }
}