package com.masterspring.backenddayoff.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequestResponse {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    private Integer status;

    private LocalDateTime createdAt;

    private String username;

    private String manager;
}
