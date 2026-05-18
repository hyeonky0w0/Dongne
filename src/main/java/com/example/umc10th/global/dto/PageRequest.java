package com.example.umc10th.global.dto;

import jakarta.validation.constraints.Min;

public record PageRequest(
        @Min(value = 0) int page,
        @Min(value = 1) int size
) {
    public PageRequest {
        if (size > 50) throw new IllegalArgumentException("size는 최대 50입니다.");
    }
}
