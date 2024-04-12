package com.masterspring.backenddayoff.service;

import com.masterspring.backenddayoff.dto.LeaveRequestStatusDto;

public interface LeaveRequestService {
    LeaveRequestStatusDto confirmLeaveRequest(Long id, LeaveRequestStatusDto leaveRequestDto);
}
