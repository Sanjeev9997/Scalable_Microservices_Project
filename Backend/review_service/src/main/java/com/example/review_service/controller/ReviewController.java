package com.example.review_service.controller;

import com.example.review_service.dto.ReviewRequest;
import com.example.review_service.dto.ReviewResponse;
import com.example.review_service.repository.ReviewRepository;
import com.example.review_service.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return new ResponseEntity<>(reviewService.createReview(reviewRequest), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable UUID id) {
        return new ResponseEntity<>(reviewService.getReviewById(id), HttpStatus.OK);
    }

    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByCouponId(@PathVariable UUID couponId) {
        return new ResponseEntity<>(reviewService.getReviewsByCouponId(couponId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@PathVariable UUID userId) {
        return new ResponseEntity<>(reviewService.getReviewsByUserId(userId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable UUID id, @Valid @RequestBody ReviewRequest reviewRequest) {
        return new ResponseEntity<>(reviewService.updateReview(id, reviewRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>("Review has been deleted", HttpStatus.OK);
    }
}
