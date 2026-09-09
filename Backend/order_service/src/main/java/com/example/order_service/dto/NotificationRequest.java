package com.example.order_service.dto;

import com.example.order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private Long orderId;
    private OrderStatus status;
}