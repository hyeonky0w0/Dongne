package com.example.umc10th.domain.review.dto;

import java.time.LocalDateTime;

public record ReviewResDTO (

    String nickname,
    String content,
    float star,
    LocalDateTime createdAt
){}

