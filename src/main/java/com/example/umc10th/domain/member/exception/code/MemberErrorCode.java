package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "존재하지 않는 멤버입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER409_1", "이미 존재하는 이메일입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}