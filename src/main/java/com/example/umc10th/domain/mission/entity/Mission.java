package com.example.umc10th.domain.mission.entity;


import com.example.umc10th.global.Security.exception.BaseEntity;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mission")
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "condtion_amount", nullable = false)
    private Integer condtionAmount;

    @Column(name = "reward_point", nullable = false)
    private Integer rewardPoint;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "start", nullable = false)
    private LocalDate start;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;


}
