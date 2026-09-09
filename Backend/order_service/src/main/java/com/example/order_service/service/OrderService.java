package com.example.order_service.service;

import com.example.order_service.dto.CreateOrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.dto.PaymentCompletedEvent;
import com.example.order_service.entity.Order;
import com.example.order_service.enums.OrderStatus;
import com.example.order_service.enums.PaymentStatus;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(CreateOrderRequest request, String idempotencyKey);
    List<OrderResponse> getOrdersByUser(Long userId);
    OrderResponse getOrderById(Long orderId);
    void cancelOrder(Long orderId);
    OrderResponse changeOrderStatus(Long orderId, OrderStatus orderStatus);
    OrderResponse shipOrder(PaymentCompletedEvent paymentCompletedEvent);

}
