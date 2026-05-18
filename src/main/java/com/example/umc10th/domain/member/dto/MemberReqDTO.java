package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.mission.enums.Address;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class MemberReqDTO {

    public record SignUpRequest(

            @NotBlank(message = "이름은 필수입니다.")
            @Size(max = 20, message = "이름은 최대 20자입니다.")
            String name,

            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
            String password,

            @NotBlank(message = "전화번호는 필수입니다.")
            @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 10~11자리 숫자여야 합니다.")
            String phoneNumber,

            @NotNull(message = "성별은 필수입니다.")
            Gender gender,              // ENUM 처리

            @NotNull(message = "생년월일은 필수입니다.")
            @Past(message = "생년월일은 과거 날짜여야 합니다.")
            LocalDate birth,

            @NotNull(message = "주소는 필수입니다.")
            Address address,

            @Size(max = 100, message = "상세 주소는 최대 100자입니다.")
            String detailAddress,

            String socialUid,
            SocialType socialType

    ) {}
}