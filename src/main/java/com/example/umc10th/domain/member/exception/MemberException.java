package com.example.umc10th.domain.member.exception;

import com.example.umc10th.domain.member.exception.code.MemberErrorCode;

public class MemberException extends RuntimeException {

  public MemberException(String message) {
    super(message);
  }

  public MemberException(MemberErrorCode errorCode) {
    super(errorCode.getMessage());
  }
}