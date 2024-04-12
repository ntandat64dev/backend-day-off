package com.masterspring.backenddayoff.service;

import com.masterspring.backenddayoff.dto.LeaveRequestStatusDto;
import com.masterspring.backenddayoff.dto.request.LeaveRequestPost;
import com.masterspring.backenddayoff.dto.response.LeaveRequestPostResponse;

public interface LeaveRequestService {
    LeaveRequestPostResponse postLeaveRequest(LeaveRequestPost leaveRequestPost);

    LeaveRequestStatusDto confirmLeaveRequest(Long id, LeaveRequestStatusDto leaveRequestDto);
}
