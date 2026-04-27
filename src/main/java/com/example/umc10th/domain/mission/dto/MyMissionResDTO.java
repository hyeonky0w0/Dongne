package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public record MyMissionResDTO(
        String storeName,
        Integer rewardPoint,
        Integer conditionAmount,
        MissionStatus status,
        boolean isSuccess
) {}