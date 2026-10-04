package com.example.Cart_Service.service.Impl;

import com.example.Cart_Service.client.InventoryClient;
import com.example.Cart_Service.client.ProductClient;
import com.example.Cart_Service.dto.AddCartItemRequest;

import com.example.Cart_Service.dto.CartResponse;
import com.example.Cart_Service.dto.InventoryResponse;
import com.example.Cart_Service.dto.ProductResponse;
import com.example.Cart_Service.model.Cart;
import com.example.Cart_Service.model.CartItem;
import com.example.Cart_Service.repository.CartRepository;
import com.example.Cart_Service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
//    private final ProductClient productClient;
    private final ProductClientService productClientService;
    private final InventoryClientService inventoryClientService;
//    private final InventoryClient inventoryClient;
    private static final Logger log =
            (Logger) LoggerFactory.getLogger(CartServiceImpl.class);

    @Cacheable(value = "carts",key = "#userId")
    @Override
    public CartResponse getCart(Long userId) {
        log.info("getCart called");
        Cart cart=cartRepository.findByUserId(userId).orElseGet(()->new Cart());
        CartResponse cartResponse=CartResponse.builder()
                .userId(cart.getUserId())
                .cartItems(cart.getCartItems())
                .totalPrice(cart.getTotalPrice())
                .build();
        log.info("getCart return");
        return cartResponse;
    }


    @Override
    public CartResponse addItem(Long userId,AddCartItemRequest request) {
        log.info("addItem called");
        log.info("product fetching");
        ProductResponse product = productClientService.getProductById(request.getProductId());
        log.info("product fetched");
        log.info("inventory fetching");
        InventoryResponse inventoryResponse=inventoryClientService.getInventory(request.getProductId());
        log.info("inventory fetched");
        if(inventoryResponse.getAvailableQuantity()<request.getQuantity()){
            throw new RuntimeException("Insufficient quantity");
        }
        log.info("cart fetching");
        Cart cart = cartRepository.findByUserId(userId).orElseGet(()->new Cart());
        log.info("cart fetched");
        boolean already=false;
        for(CartItem item:cart.getCartItems()){
            if(item.getProductId()== request.getProductId()){
                item.setQuantity(item.getQuantity()+request.getQuantity());
                already=true;
            }
        }
        if(!already) {
            cart.setUserId(userId);
            CartItem cartItem = new CartItem();
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice());
            cartItem.setProductName(product.getName());
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }
        CartResponse response=mapToResponse(cartRepository.save(cart));
        log.info("addItem completed");
        return response;
    }

    @CachePut(value = "carts",key = "#userId")
    @Override
    public CartResponse updateQuantity(Long userId, Long cartItemId, Integer quantity) {
        log.info("updateQuantity called");
        log.info("cart fetching");
        Cart cart = cartRepository.findByUserId(userId).orElseGet(()->new Cart());
        log.info("cart fetched");
        if(quantity==0) return removeItem(userId,cartItemId);

        cart.getCartItems().forEach(item -> {
            if(item.getItemId().equals(cartItemId)){
                InventoryResponse inventoryResponse=inventoryClientService.getInventory(item.getProductId());
                if(inventoryResponse.getAvailableQuantity()<quantity){
                    throw new RuntimeException("Insufficient quantity");
                }
                item.setQuantity(quantity);
            }
        });
        CartResponse response=mapToResponse(cartRepository.save(cart));
        log.info("updateQuantity completed");
        return response;
    }

    @CachePut(value = "carts",key = "#userId")
    @Override
    public CartResponse removeItem(Long userId, Long cartItemId) {
        log.info("removeItem called");
        log.info("cart fetching");
        Cart cart = cartRepository.findByUserId(userId).orElseGet(()->new Cart());
        log.info("cart fetched");
        cart.getCartItems().removeIf(item -> item.getItemId().equals(cartItemId));
        CartResponse response=mapToResponse(cartRepository.save(cart));
        log.info("removeItem completed");
        return response;
    }

    @CacheEvict(value = "carts",key = "#userId")
    @Override
    public void clearCart(Long userId) {
       log.info("clearCart called");
       log.info("cart fetching");
       Cart cart = cartRepository.findByUserId(userId).orElseGet(()->new Cart());
       log.info("cart fetched");
       cart.getCartItems().clear();
       cartRepository.save(cart);
       log.info("clearCart completed");
    }

    private CartResponse mapToResponse(Cart cart){
        return CartResponse.builder()
                .userId(cart.getUserId())
                .cartItems(cart.getCartItems())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

}
