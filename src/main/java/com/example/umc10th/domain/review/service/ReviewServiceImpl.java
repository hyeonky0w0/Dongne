package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    //리뷰 생성
    @Override
    public ReviewResDTO createReview(Long storeId, Long memberId, ReviewReqDTO request) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("store 없음"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("member 없음"));

        Review review = ReviewConverter.toEntity(request, store, member);

        reviewRepository.save(review);

        // 저장 후 DTO로 변환해서 반환
        return ReviewConverter.toDTO(review);
    }

    //리뷰 조회 (페이징)

    @Override
    public Page<ReviewResDTO> getReviews(Long storeId, int page) {

        Pageable pageable = PageRequest.of(page, 10);

        return reviewRepository.findByStoreId(storeId, pageable)
                .map(ReviewConverter::toDTO);
    }
}