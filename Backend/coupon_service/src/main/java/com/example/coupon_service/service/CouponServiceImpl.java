package com.example.coupon_service.service;

import com.example.coupon_service.dto.CouponRequest;
import com.example.coupon_service.dto.CouponResponse;
import com.example.coupon_service.entity.Coupon;
import com.example.coupon_service.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    @Override
    public CouponResponse createCoupon(CouponRequest request) {
        if(couponRepository.existsByCouponCode(request.getCouponCode())) {
            throw new RuntimeException("Coupon already exists with code : " + request.getCouponCode());
        }
        Coupon coupon =Coupon.builder()
                .couponCode(request.getCouponCode())
                .description(request.getDescription())
                .discountValue(request.getDiscountValue())
                .discountType(request.getDiscountType())
                .minimumOrderAmount(request.getMinimumOrderAmount())
                .maximumDiscount(request.getMaximumDiscount())
                .usageLimit(request.getUsageLimit())
                .usedCount(0)
                .validFrom(request.getValidFrom())
                .validTill(request.getValidTill())
                .active(request.getActive())
                .build();
        Coupon savedCoupon = couponRepository.save(coupon);

        return mapToResponse(savedCoupon);
    }

    @Override
    public CouponResponse getCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(()-> new RuntimeException("Coupon not found with id : " + id));
        return mapToResponse(coupon);
    }

    @Override
    public List<CouponResponse> getAllCoupons() {
        return couponRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public CouponResponse updateCoupon(Long id, CouponRequest request) {
        Coupon coupon=couponRepository.findById(id).orElseThrow(()-> new RuntimeException("Coupon not found with id : " + id));
        coupon.setCouponCode(request.getCouponCode());
        coupon.setDescription(request.getDescription());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setMinimumOrderAmount(request.getMinimumOrderAmount());
        coupon.setMaximumDiscount(request.getMaximumDiscount());
        coupon.setUsageLimit(request.getUsageLimit());
        coupon.setValidFrom(request.getValidFrom());
        coupon.setValidTill(request.getValidTill());
        coupon.setActive(request.getActive());

        Coupon updatedCoupon = couponRepository.save(coupon);
        return mapToResponse(updatedCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        Coupon coupon=couponRepository.findById(id).orElseThrow(()-> new RuntimeException("Coupon not found with id : " + id));
        couponRepository.delete(coupon);

    }

    @Override
    public CouponResponse getCouponByCode(String code) {
        Coupon coupon=couponRepository.findByCouponCode(code).orElseThrow(()-> new RuntimeException("Coupon not found with code : " + code));

        return mapToResponse(coupon);
    }

    private CouponResponse mapToResponse(Coupon coupon) {
        return CouponResponse.builder()
                .id(coupon.getId())
                .couponCode(coupon.getCouponCode())
                .description(coupon.getDescription())
                .discountValue(coupon.getDiscountValue())
                .discountType(coupon.getDiscountType())
                .minimumOrderAmount(coupon.getMinimumOrderAmount())
                .maximumDiscount(coupon.getMaximumDiscount())
                .usageLimit(coupon.getUsageLimit())
                .usedCount(coupon.getUsedCount())
                .validFrom(coupon.getValidFrom())
                .validTill(coupon.getValidTill())
                .active(coupon.getActive())
                .build();
    }

}
