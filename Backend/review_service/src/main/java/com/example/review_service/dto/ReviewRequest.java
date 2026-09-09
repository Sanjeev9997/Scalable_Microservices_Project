package com.example.review_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {

    @NotNull
    private UUID couponId;

    @NotNull
    private UUID userId;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;
}
