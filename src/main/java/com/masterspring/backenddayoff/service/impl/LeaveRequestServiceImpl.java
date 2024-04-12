package com.masterspring.backenddayoff.service.impl;

import com.masterspring.backenddayoff.dto.LeaveRequestStatusDto;
import com.masterspring.backenddayoff.dto.request.LeaveRequestPost;
import com.masterspring.backenddayoff.dto.response.LeaveRequestPostResponse;
import com.masterspring.backenddayoff.entity.LeaveRequest;
import com.masterspring.backenddayoff.exception.AppException;
import com.masterspring.backenddayoff.mapper.LeaveRequestPostMapper;
import com.masterspring.backenddayoff.repository.LeaveRemainRepository;
import com.masterspring.backenddayoff.repository.LeaveRequestRepository;
import com.masterspring.backenddayoff.repository.UserRepository;
import com.masterspring.backenddayoff.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;
    private final LeaveRequestPostMapper leaveRequestPostMapper;
    private final LeaveRemainRepository leaveRemainRepository;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository, LeaveRequestPostMapper leaveRequestPostMapper,
                                   LeaveRemainRepository leaveRemainRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
        this.leaveRequestPostMapper = leaveRequestPostMapper;
        this.leaveRemainRepository = leaveRemainRepository;
    }

    @Override
    public LeaveRequestPostResponse postLeaveRequest(LeaveRequestPost leaveRequestPost) {
        if (leaveRequestPost.getStartDate().isAfter(leaveRequestPost.getEndDate())) throw new AppException(400, "Start date cannot be after end date.");

        // If user not exist then throw error.
        var user = userRepository.findById(leaveRequestPost.getUserId());
        if (user.isEmpty()) throw new AppException(400, "User not found.");

        // If request days > remain days then throw error.
        var leaveRemain = leaveRemainRepository.findByUserId(user.get().getId());
        int remainDays = leaveRemain.getRemainDays();
        var starDateTime = leaveRequestPost.getStartDate().atTime(0, 0, 0);
        var endDateTime = leaveRequestPost.getEndDate().atTime(23, 59, 59);
        int days = (int) Duration.between(starDateTime, endDateTime).toDays();
        if (days > remainDays) {
            throw new AppException(400, "Request days exceeds remain days. (Remain days: %s, request days: %s)".formatted(remainDays, days));
        }

        // Save leave request to database.
        var leaveRequest = leaveRequestPostMapper.leaveRequestPostToLeaveRequest(leaveRequestPost);
        leaveRequest.setStatus(2);
        leaveRequest.setUser(user.get());
        leaveRequestRepository.save(leaveRequest);

        // Decrease leave remains.
        leaveRemain.setRemainDays(remainDays - days);

        // Return response to client.
        return leaveRequestPostMapper.leaveRequestToLeaveRequestPostResponse(leaveRequest);
    }

    @Override
    public LeaveRequestStatusDto confirmLeaveRequest(Long id, LeaveRequestStatusDto leaveRequestDto) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id).orElseThrow(()
                -> new AppException(500, "could not found"));
        if (leaveRequestDto.getStatus() == 0 || leaveRequestDto.getStatus() == 1)
            leaveRequest.setStatus(leaveRequestDto.getStatus());
        leaveRequest.setStatus(2);
        leaveRequestRepository.save(leaveRequest);
        LeaveRequestStatusDto response = new LeaveRequestStatusDto();
        response.setId(leaveRequest.getId());
        response.setStatus(leaveRequest.getStatus());
        response.setManager_id(leaveRequest.getUser().getId());
        return response;
    }
}
