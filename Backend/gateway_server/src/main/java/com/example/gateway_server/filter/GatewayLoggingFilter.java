package com.example.gateway_server.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class GatewayLoggingFilter implements GlobalFilter, Ordered {
//    private static final Logger log =
//            LoggerFactory.getLogger(GatewayLoggingFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();

        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String routeId = route != null ? route.getId() : "unknown";
        boolean authenticated = exchange.getRequest().getHeaders().getFirst("Authorization") != null;

        // The initial request log benefits from the early inbound trace thread context
        log.info("Gateway request started method={} path={} route={} authenticated={}",
                method, path, routeId, authenticated);

        return chain.filter(exchange)
                // Use doOnEach to capture trace context safely inside the reactive stream execution
                .doOnEach(signal -> {
                    if (signal.isOnError()) {
                        Throwable error = signal.getThrowable();
                        log.error("Gateway request failed method={} path={} route={} error={}",
                                method, path, routeId, error != null ? error.getMessage() : "Unknown", error);
                    }
                })
                // Execute terminal logs using a conditional callback setup to maintain tracing hooks
                .doOnSuccess(v -> logExecution(exchange, method, path, routeId, startTime))
                .doOnError(e -> logExecution(exchange, method, path, routeId, startTime));
    }

    private void logExecution(ServerWebExchange exchange, String method, String path, String routeId, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        HttpStatusCode status = exchange.getResponse().getStatusCode();
        int statusCode = status != null ? status.value() : 0;

        if (statusCode >= 500) {
            log.error("Gateway request completed method={} path={} route={} status={} durationMs={}",
                    method, path, routeId, statusCode, duration);
        } else if (statusCode >= 400) {
            log.warn("Gateway request completed method={} path={} route={} status={} durationMs={}",
                    method, path, routeId, statusCode, duration);
        } else {
            log.info("Gateway request completed method={} path={} route={} status={} durationMs={}",
                    method, path, routeId, statusCode, duration);
        }
    }

    @Override
    public int getOrder() {
        // Run slightly after early tracing filters populate the exchange
        return -2;
    }
}
