package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;

public class ReviewConverter {


    public static Review toEntity(ReviewReqDTO request, Store store, Member member) {
        return Review.builder()
                .content(request.getReviewContent())
                .star(request.getStarRating())
                .store(store)
                .member(member)
                .build();
    }

    public static ReviewResDTO toDTO(Review review) {
        return new ReviewResDTO(
                review.getMember().getName(),
                review.getContent(),
                review.getStar(),
                review.getCretedAt()
        );
    }
}
