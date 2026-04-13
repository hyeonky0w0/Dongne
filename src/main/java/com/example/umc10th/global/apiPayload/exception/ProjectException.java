package com.example.umc10th.global.apiPayload.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class ProjectException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public ProjectException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
