package com.example.shipping_service.service;

import com.example.shipping_service.dto.ShipmentRequest;
import com.example.shipping_service.dto.ShipmentResponse;
import com.example.shipping_service.enums.ShipmentStatus;

import java.util.List;

public interface ShippingService {
    ShipmentResponse createShipment(ShipmentRequest request);

    ShipmentResponse getShipment(Long id);

    List<ShipmentResponse> getAllShipments();

    ShipmentResponse updateStatus(Long id, ShipmentStatus status);

    void deleteShipment(Long id);
}
