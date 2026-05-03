package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.store.id = :storeId")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    // ID 기반 커서 (최신순)
    @Query("""
        SELECT r FROM Review r
        WHERE r.member.id = :memberId
        AND (:cursorId IS NULL OR r.id < :cursorId)
        ORDER BY r.id DESC
    """)
    List<Review> findByMemberIdOrderById(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    // 별점 기반 커서 (별점 높은 순, 같으면 ID 최신순)
    @Query("""
        SELECT r FROM Review r
        WHERE r.member.id = :memberId
        AND (
            :cursorStar IS NULL
            OR r.star < :cursorStar
            OR (r.star = :cursorStar AND r.id < :cursorId)
        )
        ORDER BY r.star DESC, r.id DESC
    """)
    List<Review> findByMemberIdOrderByStar(
            @Param("memberId") Long memberId,
            @Param("cursorStar") Double cursorStar,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}