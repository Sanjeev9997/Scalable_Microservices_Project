package com.example.coupon_service.controller;

import com.example.coupon_service.dto.CouponRequest;
import com.example.coupon_service.dto.CouponResponse;
import com.example.coupon_service.entity.Coupon;
import com.example.coupon_service.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@RequestBody CouponRequest coupon) {
        return new ResponseEntity<>(couponService.createCoupon(coupon), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        return new ResponseEntity<>(couponService.getAllCoupons(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> getCouponById(@PathVariable Long id) {
        return new ResponseEntity<>(couponService.getCoupon(id), HttpStatus.OK);
    }

    @GetMapping("/code/{couponCode}")
    public ResponseEntity<CouponResponse> getCouponByCode(@PathVariable String couponCode) {
        return new ResponseEntity<>(couponService.getCouponByCode(couponCode), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> updateCoupon(@PathVariable Long id, @RequestBody CouponRequest coupon) {
        CouponResponse couponResponse = couponService.updateCoupon(id, coupon);
        return new ResponseEntity<>(couponResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long id) {
       couponService.deleteCoupon(id);
       return new ResponseEntity<>("Coupon deleted successfully.",HttpStatus.OK);
    }
}
