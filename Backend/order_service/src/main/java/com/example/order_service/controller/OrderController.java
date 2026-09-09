package com.example.order_service.controller;

import com.example.order_service.dto.CreateOrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.enums.OrderStatus;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
   private final OrderService orderService;

   @PostMapping
    public ResponseEntity<OrderResponse> createOrder( @RequestHeader("Idempotency-Key")
                                                         String idempotencyKey,@RequestBody CreateOrderRequest request) {
       return ResponseEntity.ok(orderService.placeOrder(request,idempotencyKey));
   }

   @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrder(@PathVariable Long userId) {
       return ResponseEntity.ok(orderService.getOrdersByUser(userId));
   }

   @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
       return ResponseEntity.ok(orderService.getOrderById(orderId));
   }

   @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        return new ResponseEntity<>("Order has been deleted", HttpStatus.OK);
   }

   @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponse> changeOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus orderStatus) {
       return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orderStatus));
   }
}
