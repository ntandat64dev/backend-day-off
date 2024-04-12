package com.masterspring.backenddayoff.controller;

import com.masterspring.backenddayoff.dto.LeaveRequestStatusDto;
import com.masterspring.backenddayoff.service.LeaveRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leave_request")
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PutMapping("/update_status/{id}")
    public ResponseEntity<LeaveRequestStatusDto> updateStatus(
            @PathVariable("id") long id,
            @RequestBody LeaveRequestStatusDto leaveRequestDto) {
        return ResponseEntity.ok(leaveRequestService.confirmLeaveRequest(id, leaveRequestDto));
    }
}
