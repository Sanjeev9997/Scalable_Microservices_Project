package com.example.shipping_service.entity;

import com.example.shipping_service.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String trackingNumber;

    private String customerName;

    private String shippingAddress;

    private String courierPartner;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;
}
