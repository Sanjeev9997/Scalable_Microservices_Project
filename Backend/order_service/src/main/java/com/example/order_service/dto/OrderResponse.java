package com.example.order_service.dto;

import com.example.order_service.entity.OrderItem;
import com.example.order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Long userId;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<OrderItem> orderItems;

}
