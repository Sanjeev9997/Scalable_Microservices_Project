package com.example.payment_service.entity;

import com.example.payment_service.enums.PaymentMethod;
import com.example.payment_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Long orderId;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod  paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDateTime paymentDate;

}
