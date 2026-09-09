package com.example.Cart_Service.service;


import com.example.Cart_Service.dto.AddCartItemRequest;
import com.example.Cart_Service.dto.CartResponse;
import com.example.Cart_Service.model.Cart;

public interface CartService {

    CartResponse getCart(Long userId);

    CartResponse addItem(Long userId,
                 AddCartItemRequest request);

    CartResponse updateQuantity(Long userId,
                        Long cartItemId,
                        Integer quantity);

    CartResponse removeItem(Long userId,
                    Long cartItemId);

    void clearCart(Long userId);


}
