package com.example.umc10th.global.apiPayload.handler;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {

    // 프로젝트 커스텀 예외 처리
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectException(ProjectException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        log.warn("[ProjectException] code={}, message={}", errorCode, e.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    // @Valid 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("[ValidationException] {}", errorMessage);

        BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, errorMessage));
    }

    // JSON 파싱 오류 처리 (잘못된 형식, 알 수 없는 enum 값 등)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleJsonParseException(
            HttpMessageNotReadableException e
    ) {
        log.warn("[JsonParseException] {}", e.getMessage());
        BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, "요청 데이터 형식이 올바르지 않습니다."));
    }

    // Multipart 파일 크기 초과 처리
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<String>> handleMaxUploadSizeException(
            MaxUploadSizeExceededException e
    ) {
        log.warn("[MaxUploadSizeExceededException] {}", e.getMessage());
        BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, "파일 크기가 허용 범위를 초과했습니다."));
    }

    // 그 외 모든 예외 — 내부 메시지 노출 없이 로그로만 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        log.error("[UnhandledException] {}", e.getMessage(), e);
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, "서버 내부 오류가 발생했습니다."));
    }
}