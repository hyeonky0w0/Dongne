package com.example.umc10th.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
public class MyMissionResDTO {
    public record MyMissionDTO(

            Long missionId,
            String missionName,
            String condition,
            int reward,

            LocalDate deadline,
            long dDay,

            boolean isCompleted

    ) {}
}