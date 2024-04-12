package com.masterspring.backenddayoff.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "leave_requests")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Long id;

    @Column(name = "`start_date`", nullable = false)
    private LocalDate startDate;

    @Column(name = "`end_date`", nullable = false)
    private LocalDate endDate;

    @Column(name = "`reason`", nullable = false)
    private String reason;

    /**
     * 0 - Accepted
     * 1 - Rejected
     * 2 - Waiting
     */
    @Column(name = "`status`", nullable = false)
    private Integer status;

    @Column(name = "`created_at`", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "`user_id`")
    private User user;
}