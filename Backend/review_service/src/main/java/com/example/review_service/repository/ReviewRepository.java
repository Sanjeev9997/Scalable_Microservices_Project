package com.example.review_service.repository;

import com.example.review_service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByCouponId(UUID couponId);
    List<Review> findByUserId(UUID userId);
}
