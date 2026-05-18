package com.example.umc10th.global.dto;

import java.util.List;

public record CursorPageResponse<T>(
        List<T> content,
        String nextCursor,   // ← "3.5_42" 또는 "42" 또는 null
        boolean hasNext
) {
    public static <T> CursorPageResponse<T> ofStar(
            List<T> content, String nextCursor, boolean hasNext) {
        return new CursorPageResponse<>(content, nextCursor, hasNext);
    }

    public static <T> CursorPageResponse<T> ofId(
            List<T> content, String nextCursor, boolean hasNext) {
        return new CursorPageResponse<>(content, nextCursor, hasNext);
    }
}
