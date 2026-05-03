package com.example.umc10th.domain.review.dto;

import java.time.LocalDateTime;

public class ReviewResDTO {


    public record CreateResponse(
            String nickname,
            String content,
            Float star,
            LocalDateTime createdAt
    ) {}


    public record ReviewItem(
            Long reviewId,
            String nickname,
            Float star,
            String content,
            LocalDateTime createdAt
    ) {}
}