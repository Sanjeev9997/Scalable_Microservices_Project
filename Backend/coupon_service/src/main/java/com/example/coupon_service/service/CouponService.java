package com.example.coupon_service.service;


import com.example.coupon_service.dto.CouponRequest;
import com.example.coupon_service.dto.CouponResponse;

import java.util.List;

public interface CouponService {
    CouponResponse createCoupon(CouponRequest request);

    CouponResponse getCoupon(Long id);

    List<CouponResponse> getAllCoupons();

    CouponResponse updateCoupon(Long id, CouponRequest request);

    void deleteCoupon(Long id);

    CouponResponse getCouponByCode(String code);
}
