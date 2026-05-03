package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.*;


public record ReviewReqDTO(

        @NotNull(message = "별점은 필수입니다.")
        @Min(value = 1, message = "별점은 최소 1점입니다.")
        @Max(value = 5, message = "별점은 최대 5점입니다.")
        Integer starRating,

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        @Size(max = 500, message = "리뷰 내용은 최대 500자입니다.")
        String reviewContent

) {}
