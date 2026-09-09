package com.example.order_service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long itemId;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private CartResponse cart;
}
