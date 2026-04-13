package com.example.umc10th.domain.member.dto;

import org.apache.naming.factory.SendMailFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberReqDTO {
    public record RequestBody(
            String email,
            String password,
            String Name,
            String gender,
            LocalDate birth,
            String address
        ) {}
    }

