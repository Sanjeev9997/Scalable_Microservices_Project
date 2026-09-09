package com.example.review_service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private UUID id;

    private UUID couponId;

    private UUID userId;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
}
