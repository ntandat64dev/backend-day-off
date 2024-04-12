package com.masterspring.backenddayoff.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class LeaveRequestPostResponse {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt;

    private Long createdBy;

    private String reason;

    private Integer status;
}
