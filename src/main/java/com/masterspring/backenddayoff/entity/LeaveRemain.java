package com.masterspring.backenddayoff.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "`leave_remains`")
public class LeaveRemain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Long id;

    @Column(name = "`remain_days`")
    private Integer remainDays;

    @Column(name = "`year`")
    private Integer year;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
