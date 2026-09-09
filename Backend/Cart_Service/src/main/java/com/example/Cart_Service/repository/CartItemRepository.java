package com.example.Cart_Service.repository;

import com.example.Cart_Service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
