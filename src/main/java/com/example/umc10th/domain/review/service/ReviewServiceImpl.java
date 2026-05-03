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
import com.example.umc10th.global.dto.CursorPageResponse;
import com.example.umc10th.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ReviewResDTO.CreateResponse createReview(Long storeId, Long memberId, ReviewReqDTO.CreateRequest request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ProjectException(StoreErrorCode.STORE_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

        Review review = ReviewConverter.toEntity(request, store, member);
        reviewRepository.save(review);

        return ReviewConverter.toDTO(review);
    }

    @Override
    public PageResponse<ReviewResDTO.CreateResponse> getReviews(Long storeId, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<ReviewResDTO.CreateResponse> result = reviewRepository.findByStoreId(storeId, pageable)
                .map(ReviewConverter::toDTO);

        return PageResponse.from(result);
    }

    @Override
    public CursorPageResponse<ReviewResDTO.ReviewItem> getMyReviews(
            ReviewReqDTO.CursorRequest request
    ) {
        // size + 1개 가져와서 hasNext 판단
        Pageable pageable = PageRequest.of(0, request.size() + 1);

        List<Review> reviews;

        if (request.sortType() == ReviewReqDTO.SortType.STAR_DESC) {
            reviews = reviewRepository.findByMemberIdOrderByStar(
                    request.memberId(),
                    request.cursorStar(),
                    request.cursorId() != null ? request.cursorId() : Long.MAX_VALUE,
                    pageable
            );
        } else {
            // ID_DESC (기본)
            reviews = reviewRepository.findByMemberIdOrderById(
                    request.memberId(),
                    request.cursorId(),
                    pageable
            );
        }

        boolean hasNext = reviews.size() > request.size();

        // hasNext면 마지막 초과분 제거
        List<Review> content = hasNext
                ? reviews.subList(0, request.size())
                : reviews;

        List<ReviewResDTO.ReviewItem> items = content.stream()
                .map(ReviewConverter::toReviewItem)
                .toList();

        // 다음 커서값 = 마지막 항목 기준
        Long nextCursorId = hasNext ? content.get(content.size() - 1).getId() : null;

        if (request.sortType() == ReviewReqDTO.SortType.STAR_DESC) {
            Double nextCursorStar = hasNext
                    ? (double) content.get(content.size() - 1).getStar()
                    : null;
            return CursorPageResponse.ofStar(items, nextCursorId, nextCursorStar, hasNext);
        }

        return CursorPageResponse.ofId(items, nextCursorId, hasNext);
    }
}