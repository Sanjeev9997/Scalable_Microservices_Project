package com.example.shipping_service.dto;
import lombok.Data;


@Data
public class ShipmentRequest {
    private Long orderId;
    private String customerName;
    private String shippingAddress;
    private String courierPartner;
}
