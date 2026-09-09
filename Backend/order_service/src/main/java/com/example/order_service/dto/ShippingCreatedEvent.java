package com.example.order_service.dto;


import com.example.order_service.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingCreatedEvent {
    private Long orderId;
    private String customerName;
    private String shippingAddress;
    private String courierPartner;
    private ShipmentStatus status;
}

