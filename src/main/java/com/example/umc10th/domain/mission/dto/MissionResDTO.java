package com.example.umc10th.domain.mission.dto;

import java.time.LocalDate;

public record MissionResDTO(
        Long missionId,
        String storeName,
        String storeCategory,
        String missionTitle,
        String missionContent,
        Integer conditionAmount,
        Integer rewardPoint,
        LocalDate deadline,
        long dDay,
        String status
) {}