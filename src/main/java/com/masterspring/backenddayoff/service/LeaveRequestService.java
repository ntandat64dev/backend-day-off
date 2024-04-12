package com.masterspring.backenddayoff.service;

import com.masterspring.backenddayoff.dto.LeaveRequestStatusDto;
import com.masterspring.backenddayoff.dto.request.LeaveRequestPost;
import com.masterspring.backenddayoff.dto.response.LeaveRequestPaginationResponse;
import com.masterspring.backenddayoff.dto.response.LeaveRequestPostResponse;

public interface LeaveRequestService {
    LeaveRequestPostResponse postLeaveRequest(LeaveRequestPost leaveRequestPost);
    LeaveRequestStatusDto confirmLeaveRequest(Long id, LeaveRequestStatusDto leaveRequestDto);
    LeaveRequestPaginationResponse getPageLeaveRequests(int pageNo, int pageSize);
    LeaveRequestPaginationResponse getPageLeaveRequestsWithUserId(Long userId, Integer pageNo, Integer pageSize);
    LeaveRequestPaginationResponse getPageLeaveRequestsBySearch(String keyword, Integer pageNo, Integer pageSize);
}
