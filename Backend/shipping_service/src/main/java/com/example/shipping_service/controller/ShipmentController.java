package com.example.shipping_service.controller;

import com.example.shipping_service.dto.ShipmentRequest;
import com.example.shipping_service.dto.ShipmentResponse;
import com.example.shipping_service.enums.ShipmentStatus;
import com.example.shipping_service.repository.ShipmentRepository;
import com.example.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shippings")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShippingService shippingService;

    @PostMapping
    public ResponseEntity<ShipmentResponse> createShipment(@RequestBody ShipmentRequest shipmentRequest) {
        return new ResponseEntity<>(shippingService.createShipment(shipmentRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ShipmentResponse>> getAllShipments() {
        return new ResponseEntity<>(shippingService.getAllShipments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponse> getShipment(@PathVariable Long id) {
        return new ResponseEntity<>(shippingService.getShipment(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ShipmentResponse> updateShipment(@PathVariable Long id, @RequestParam ShipmentStatus status) {
        return new ResponseEntity<>(shippingService.updateStatus(id,status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long id) {
        shippingService.deleteShipment(id);
        return new ResponseEntity<>("Shipment Deleted",HttpStatus.OK);
    }


}
