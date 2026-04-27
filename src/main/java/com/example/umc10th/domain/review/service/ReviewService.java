package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;

public interface ReviewService {

    public ReviewResDTO createReview(Long storeId, Long memberId, ReviewReqDTO request);

    Page<ReviewResDTO> getReviews(Long storeId, int page);
};