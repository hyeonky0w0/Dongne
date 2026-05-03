package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    public ResponseEntity<?> createReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewReqDTO request,
            @RequestParam Long memberId
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(reviewService.createReview(storeId, memberId, request)));
    }
}
