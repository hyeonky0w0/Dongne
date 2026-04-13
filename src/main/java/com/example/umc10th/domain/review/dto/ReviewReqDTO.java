package com.example.umc10th.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReqDTO {

    private int starRating;     // 별점
    private String reviewContent;  // 리뷰 내용
}
