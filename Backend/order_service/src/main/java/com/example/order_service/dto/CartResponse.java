package com.example.order_service.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long cartId;
    private Long userId;
    private List<CartItem> cartItems=new ArrayList<>();

    public Double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(item ->
                        item.getPrice() * item.getQuantity())
                .sum();
    }
}
