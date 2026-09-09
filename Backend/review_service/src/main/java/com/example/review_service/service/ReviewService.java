package com.example.review_service.service;


import com.example.review_service.dto.ReviewRequest;
import com.example.review_service.dto.ReviewResponse;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request);

    ReviewResponse getReviewById(UUID id);

    List<ReviewResponse> getAllReviews();

    List<ReviewResponse> getReviewsByCouponId(UUID couponId);

    List<ReviewResponse> getReviewsByUserId(UUID userId);

    ReviewResponse updateReview(UUID id, ReviewRequest request);

    void deleteReview(UUID id);
}
