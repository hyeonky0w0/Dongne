package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import com.example.umc10th.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ReviewResDTO createReview(Long storeId, Long memberId, ReviewReqDTO request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ProjectException(StoreErrorCode.STORE_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

        Review review = ReviewConverter.toEntity(request, store, member);
        reviewRepository.save(review);

        return ReviewConverter.toDTO(review);
    }

    @Override
    public PageResponse<ReviewResDTO> getReviews(Long storeId, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<ReviewResDTO> result = reviewRepository.findByStoreId(storeId, pageable)
                .map(ReviewConverter::toDTO);

        return PageResponse.from(result);
    }
}