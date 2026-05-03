package com.example.umc10th.global.dto;

import java.util.List;

public record CursorPageResponse<T>(
        List<T> content,
        Long nextCursorId,
        Double nextCursorStar,
        boolean hasNext
) {
    // ID 기반 커서
    public static <T> CursorPageResponse<T> ofId(
            List<T> content,
            Long nextCursorId,
            boolean hasNext
    ) {
        return new CursorPageResponse<>(content, nextCursorId, null, hasNext);
    }

    // 별점 기반 커서
    public static <T> CursorPageResponse<T> ofStar(
            List<T> content,
            Long nextCursorId,
            Double nextCursorStar,
            boolean hasNext
    ) {
        return new CursorPageResponse<>(content, nextCursorId, nextCursorStar, hasNext);
    }
}