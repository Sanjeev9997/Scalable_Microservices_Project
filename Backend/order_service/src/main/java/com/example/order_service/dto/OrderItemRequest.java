package com.example.order_service.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
    private Double price;
    private String productName;
}
