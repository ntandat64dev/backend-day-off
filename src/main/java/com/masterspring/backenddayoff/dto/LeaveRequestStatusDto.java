package com.masterspring.backenddayoff.dto;


import lombok.Data;

@Data
public class LeaveRequestStatusDto {
    private long id;
    private Integer status;
    private long manager_id;
}
