package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_404", "회원을 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER_409", "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER_401", "비밀번호가 올바르지 않습니다."),
    NOT_SUPPORT_SOCIAL_PROVIDER(HttpStatus.BAD_REQUEST, "MEMBER_400", "지원하지 않는 소셜 로그인입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}