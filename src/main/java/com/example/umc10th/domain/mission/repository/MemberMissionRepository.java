package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    Page<MemberMission> findByMemberId(Long memberId, Pageable pageable);

    @Query("SELECT COUNT(mm) FROM MemberMission mm WHERE mm.member.id = :memberId AND mm.isComplete = true")
    int countCompletedByMemberId(@Param("memberId") Long memberId);


    @Query("""
        SELECT mm FROM MemberMission mm
        JOIN FETCH mm.mission m
        JOIN FETCH m.store s
        JOIN FETCH s.location l
        WHERE mm.member.id = :memberId
          AND l.name = :address
          AND mm.isComplete = :isComplete
    """)
    Page<MemberMission> findMissionsByMemberAndAddress(
            @Param("memberId") Long memberId,
            @Param("address") Address address,
            @Param("isComplete") boolean isComplete,
            Pageable pageable
    );

}