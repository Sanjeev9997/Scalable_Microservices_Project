package com.example.review_service.service;

import com.example.review_service.dto.ReviewRequest;
import com.example.review_service.dto.ReviewResponse;
import com.example.review_service.entity.Review;
import com.example.review_service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        Review review =Review.builder()
                .couponId(request.getCouponId())
                .userId(request.getUserId())
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse getReviewById(UUID id) {
        Review review = reviewRepository.findById(id).orElseThrow(()->new RuntimeException("Review not found"));

        return mapToResponse(review);
    }

    @Override
    public List<ReviewResponse> getAllReviews() {
       return reviewRepository.findAll()
               .stream().map(this::mapToResponse).collect(Collectors.toList());


    }

    @Override
    public List<ReviewResponse> getReviewsByCouponId(UUID couponId) {

        return reviewRepository.findByCouponId(couponId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getReviewsByUserId(UUID userId) {
        return reviewRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ReviewResponse updateReview(UUID id, ReviewRequest request) {
        Review review = reviewRepository.findById(id).orElseThrow(()->new RuntimeException("Review not found"));
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());
        review.setCouponId(request.getCouponId());

        return mapToResponse(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(UUID id) {
        Review review = reviewRepository.findById(id).orElseThrow(()->new RuntimeException("Review not found"));
        reviewRepository.delete(review);
    }

    private ReviewResponse mapToResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .couponId(review.getCouponId())
                .userId(review.getUserId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();

    }
}
