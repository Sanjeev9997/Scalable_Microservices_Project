package com.example.coupon_service.dto;


import com.example.coupon_service.entity.DiscountType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponRequest {
    private String couponCode;

    private String description;

    private Double discountValue;

    private DiscountType discountType;

    private Double minimumOrderAmount;

    private Double maximumDiscount;

    private Integer usageLimit;

    private LocalDateTime validFrom;

    private LocalDateTime validTill;

    private Boolean active;
}
