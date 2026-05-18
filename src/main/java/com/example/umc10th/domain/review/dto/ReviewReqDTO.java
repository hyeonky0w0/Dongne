package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.*;

public class ReviewReqDTO {

        public record CreateRequest(
                @NotNull(message = "별점은 필수입니다.")
                @Min(value = 1, message = "별점은 최소 1점입니다.")
                @Max(value = 5, message = "별점은 최대 5점입니다.")
                Integer starRating,

                @NotBlank(message = "리뷰 내용은 필수입니다.")
                @Size(max = 500, message = "리뷰 내용은 최대 500자입니다.")
                String reviewContent
        ) {}

        public record CursorRequest(

                @NotNull(message = "사용자 ID는 필수입니다.")
                Long memberId,

                String cursor,

                @NotNull(message = "정렬 기준은 필수입니다.")
                SortType sortType,

                @NotNull(message = "페이지 크기는 필수입니다.")
                @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
                Integer size
        ) {}

        public enum SortType {
                ID_DESC,    // ID 순 (최신순)
                STAR_DESC   // 별점 높은 순
        }
}