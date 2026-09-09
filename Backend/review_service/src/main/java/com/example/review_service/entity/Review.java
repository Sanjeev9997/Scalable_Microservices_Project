package com.example.review_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID couponId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
}
