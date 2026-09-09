package com.example.coupon_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String couponCode;

    private String description;

    private Double discountValue;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Double minimumOrderAmount;

    private Double maximumDiscount;

    private Integer usageLimit;

    private Integer usedCount;

    private LocalDateTime validFrom;

    private LocalDateTime validTill;

    private Boolean active;
}
