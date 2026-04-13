package com.example.umc10th.domain.mission.dto;

import java.time.LocalDate;

public class MissionResDTO {
    public record MissionDTO(
            Long missionId,
            String missionName,
            String condition,
            Integer reward,
            LocalDate deadline,
            long dDay,
            boolean isCompleted,

            // 버튼 상태 (예: 참여가능 / 완료 / 진행중)
            String buttonStatus
    ){}
}
