package com.example.shipping_service.dto;

import com.example.shipping_service.enums.ShipmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShipmentResponse {
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String customerName;
    private String shippingAddress;
    private String courierPartner;
    private ShipmentStatus status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
}
