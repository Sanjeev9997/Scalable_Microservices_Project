package com.example.shipping_service.service;

import com.example.shipping_service.dto.ShipmentRequest;
import com.example.shipping_service.dto.ShipmentResponse;
import com.example.shipping_service.entity.Shipment;
import com.example.shipping_service.enums.ShipmentStatus;
import com.example.shipping_service.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShippingService{

    private final ShipmentRepository shipmentRepository;
    private static final Logger log= LoggerFactory.getLogger(ShipmentServiceImpl.class);
    @Override
    public ShipmentResponse createShipment(ShipmentRequest request) {
        log.info("createShipment called");
        Shipment shipment = Shipment.builder()
                .orderId(request.getOrderId())
                .customerName(request.getCustomerName())
                .shippingAddress(request.getShippingAddress())
                .courierPartner(request.getCourierPartner())
                .trackingNumber(UUID.randomUUID().toString())
                .status(ShipmentStatus.CREATED)
                .shippedAt(LocalDateTime.now())
                .build();
        log.info("Saving shipment to db");
        Shipment savedShipment=shipmentRepository.save(shipment);
        log.info("Shipment saved");

        log.info("create shipment Completed");
        return mapToResponse(savedShipment);
    }

    @Override
    public ShipmentResponse getShipment(Long id) {
        log.info("Fetching shipment");
        Shipment shipment = shipmentRepository.findById(id).orElseThrow(()->new RuntimeException("Shipment not found"));
        log.info("Fetching shipment Completed");
        return mapToResponse(shipment);
    }

    @Override
    public List<ShipmentResponse> getAllShipments() {
        log.info("Fetching all shipments");
        List<ShipmentResponse> shipmentResponses=shipmentRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
        log.info("Fetching all shipments Completed");
        return shipmentResponses;

    }

    @Override
    public ShipmentResponse updateStatus(Long id, ShipmentStatus status) {
        log.info("Fetching shipment by id");
        Shipment shipment = shipmentRepository.findById(id).orElseThrow(()->new RuntimeException("Shipment not found"));
        log.info("Fetching shipment by id completed");
        shipment.setStatus(status);
        log.info("Saving changed shipment");
        Shipment savedShipment=shipmentRepository.save(shipment);
        log.info("Shipment changed");
        return mapToResponse(savedShipment);

    }

    @Override
    public void deleteShipment(Long id) {
        log.info("Fetching shipment by id");
        Shipment shipment = shipmentRepository.findById(id).orElseThrow(()->new RuntimeException("Shipment not found"));
        log.info("Fetching shipment by id completed");
        log.info("Deleting shipment by id");
        shipmentRepository.delete(shipment);
        log.info("Deleted shipment by id");
    }
    private ShipmentResponse mapToResponse(Shipment shipment) {
        return ShipmentResponse.builder()
                .id(shipment.getId())
                .orderId(shipment.getOrderId())
                .trackingNumber(shipment.getTrackingNumber())
                .customerName(shipment.getCustomerName())
                .shippingAddress(shipment.getShippingAddress())
                .courierPartner(shipment.getCourierPartner())
                .status(shipment.getStatus())
                .shippedAt(shipment.getShippedAt())
                .deliveredAt(shipment.getDeliveredAt())
                .build();
    }
}
