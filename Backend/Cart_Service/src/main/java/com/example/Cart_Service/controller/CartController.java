package com.example.Cart_Service.controller;

import com.example.Cart_Service.dto.AddCartItemRequest;
import com.example.Cart_Service.dto.CartResponse;
import com.example.Cart_Service.dto.UpdateQuantityRequest;
import com.example.Cart_Service.model.Cart;
import com.example.Cart_Service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        return new ResponseEntity<>(cartService.getCart(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartResponse> addCart(@PathVariable Long userId,@RequestBody AddCartItemRequest addCartItemRequest) {
        return new ResponseEntity<>(cartService.addItem(userId,addCartItemRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<CartResponse> updateQuantity(@PathVariable Long userId, @PathVariable Long cartItemId, @RequestBody UpdateQuantityRequest request) {
        return new ResponseEntity<>(cartService.updateQuantity(userId,cartItemId,request.getQuantity()),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return new ResponseEntity<>("Cart has been cleared",HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<CartResponse> removeItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId) {

        return new ResponseEntity<>(cartService.removeItem(
                userId,
                cartItemId
        ),HttpStatus.OK);
    }
}
