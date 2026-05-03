package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;

public class ReviewConverter {


    public static Review toEntity(ReviewReqDTO.CreateRequest request, Store store, Member member) {
        return Review.builder()
                .content(request.reviewContent())
                .star(request.starRating())
                .store(store)
                .member(member)
                .build();
    }

    public static ReviewResDTO.CreateResponse toDTO(Review review) {
        return new ReviewResDTO.CreateResponse(
                review.getMember().getName(),
                review.getContent(),
                review.getStar(),
                review.getCreatedAt()
        );
    }

    public static ReviewResDTO.ReviewItem toReviewItem(Review review) {
        return new ReviewResDTO.ReviewItem(
                review.getId(),
                review.getMember().getName(),
                review.getStar(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
