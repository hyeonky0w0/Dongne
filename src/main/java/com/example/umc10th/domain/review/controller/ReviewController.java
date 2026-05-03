package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.dto.CursorPageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/store/{storeId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResDTO.CreateResponse>> createReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewReqDTO.CreateRequest request,
            @RequestParam Long memberId
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(
                        reviewService.createReview(storeId, memberId, request)
                ));
    }

    @PostMapping("/my-reviews/search")
    public ResponseEntity<ApiResponse<CursorPageResponse<ReviewResDTO.ReviewItem>>> getMyReviews(
            @RequestBody @Valid ReviewReqDTO.CursorRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(reviewService.getMyReviews(request))
        );
    }
}