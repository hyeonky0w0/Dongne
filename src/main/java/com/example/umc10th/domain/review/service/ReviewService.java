package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.global.dto.CursorPageResponse;
import com.example.umc10th.global.dto.PageResponse;

public interface ReviewService {

    ReviewResDTO.CreateResponse createReview(Long storeId, Long memberId, ReviewReqDTO.CreateRequest request);

    PageResponse<ReviewResDTO.CreateResponse> getReviews(Long storeId, int page);

    CursorPageResponse<ReviewResDTO.ReviewItem> getMyReviews(ReviewReqDTO.CursorRequest request);
};