package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.HomeResDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.dto.MyMissionDTO;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MissionConverter {

    public static MyMissionDTO.Response toMyMissionDTO(MemberMission mm) {

        boolean isComplete = mm.isComplete();

        return new MyMissionDTO.Response(
                mm.getMission().getStore().getName(),
                mm.getMission().getRewardPoint(),
                mm.getMission().getCondtionAmount(),
                isComplete ? MissionStatus.COMPLETED : MissionStatus.ONGOING,
                isComplete
        );
    }

    // 홈 상단 정보
    public static HomeResDTO toHomeDTO(Member member, int completedMissionCount) {
        return new HomeResDTO(
                member.getAddress().name(),
                member.getPoint(),
                completedMissionCount
        );
    }

    // 홈 미션 목록
    public static MissionResDTO toMissionDTO(MemberMission mm) {
        var mission  = mm.getMission();
        var store    = mission.getStore();
        LocalDate today    = LocalDate.now();
        LocalDate deadline = mission.getDeadline();
        long dDay = ChronoUnit.DAYS.between(today, deadline);

        return new MissionResDTO(
                mission.getId(),
                store.getName(),
                store.getCategory().name(),
                mission.getTitle(),
                mission.getContent(),
                mission.getCondtionAmount(),
                mission.getRewardPoint(),
                deadline,
                dDay,
                mm.isComplete() ? "COMPLETED" : "ONGOING"
        );
    }
}