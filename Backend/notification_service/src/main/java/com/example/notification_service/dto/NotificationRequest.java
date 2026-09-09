package com.example.notification_service.dto;

import com.example.notification_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String orderId;
    private OrderStatus status;
}
