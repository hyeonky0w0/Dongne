package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    void createReview(
            Long storeId,
            Long memberId,
            ReviewReqDTO request,
            List<MultipartFile> images
    );
}