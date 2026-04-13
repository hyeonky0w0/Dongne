package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/store/{storeId}/reviews")
    public ResponseEntity<Void> createReview(
            @PathVariable Long storeId,
            @RequestPart("data") ReviewReqDTO request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestParam Long memberId   // 임시
    ) {
        reviewService.createReview(storeId, memberId, request, images);
        return ResponseEntity.ok().build();
    }
}
