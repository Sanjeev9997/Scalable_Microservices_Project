package com.example.order_service.entity;

import com.example.order_service.enums.OrderStatus;
import com.example.order_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long userId;
    private Double totalAmount;
    private String address;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private LocalDateTime orderDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    List<OrderItem> orderItems;

}
