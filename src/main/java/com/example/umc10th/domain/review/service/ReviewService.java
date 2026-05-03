package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.global.dto.PageResponse;

public interface ReviewService {

    ReviewResDTO createReview(Long storeId, Long memberId, ReviewReqDTO request);
    PageResponse<ReviewResDTO> getReviews(Long storeId, int page);
};