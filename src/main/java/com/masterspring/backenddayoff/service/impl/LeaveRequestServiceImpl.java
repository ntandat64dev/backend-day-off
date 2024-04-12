package com.masterspring.backenddayoff.service.impl;

import com.masterspring.backenddayoff.dto.LeaveRequestStatusDto;
import com.masterspring.backenddayoff.dto.request.LeaveRequestPost;
import com.masterspring.backenddayoff.dto.response.LeaveRequestPaginationResponse;
import com.masterspring.backenddayoff.dto.response.LeaveRequestPostResponse;
import com.masterspring.backenddayoff.dto.response.LeaveRequestResponse;
import com.masterspring.backenddayoff.entity.LeaveRequest;
import com.masterspring.backenddayoff.exception.AppException;
import com.masterspring.backenddayoff.mapper.LeaveRequestPostMapper;
import com.masterspring.backenddayoff.repository.LeaveRemainRepository;
import com.masterspring.backenddayoff.repository.LeaveRequestRepository;
import com.masterspring.backenddayoff.repository.UserRepository;
import com.masterspring.backenddayoff.service.LeaveRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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
        if (leaveRequestDto.getStatus() == 0 || leaveRequestDto.getStatus() == 1){
            leaveRequest.setStatus(leaveRequestDto.getStatus());
        }else{
            leaveRequest.setStatus(2);
        }
        leaveRequestRepository.save(leaveRequest);
        LeaveRequestStatusDto response = new LeaveRequestStatusDto();
        response.setId(leaveRequest.getId());
        response.setStatus(leaveRequest.getStatus());
        response.setManager_id(leaveRequest.getUser().getId());
        return response;
    }

    @Override
    public LeaveRequestPaginationResponse getPageLeaveRequests(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.findAll(pageable);
        List<LeaveRequestResponse> content = leaveRequestPage.getContent().stream().map(this::mapToResponse).collect(Collectors.toList());
        LeaveRequestPaginationResponse response = new LeaveRequestPaginationResponse();
        response.setContent(content);
        response.setPageNo(leaveRequestPage.getNumber());
        response.setPageSize(leaveRequestPage.getSize());
        response.setTotalElements(leaveRequestPage.getTotalElements());
        response.setTotalPages(leaveRequestPage.getTotalPages());
        response.setLast(leaveRequestPage.isLast());
        return response;
    }

    @Override
    public LeaveRequestPaginationResponse getPageLeaveRequestsWithUserId(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.findAllById(userId, pageable);
        List<LeaveRequestResponse> content = leaveRequestPage.getContent().stream().map(this::mapToResponse).collect(Collectors.toList());
        LeaveRequestPaginationResponse response = new LeaveRequestPaginationResponse();
        response.setContent(content);
        response.setPageNo(leaveRequestPage.getNumber());
        response.setPageSize(leaveRequestPage.getSize());
        response.setTotalElements(leaveRequestPage.getTotalElements());
        response.setTotalPages(leaveRequestPage.getTotalPages());
        response.setLast(leaveRequestPage.isLast());
        return response;
    }

    @Override
    public LeaveRequestPaginationResponse getPageLeaveRequestsBySearch(String keyword, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.findAllByUserFullNameContainingIgnoreCaseOrReasonContainingIgnoreCase(keyword, keyword, pageable);
        List<LeaveRequestResponse> content = leaveRequestPage.getContent().stream().map(this::mapToResponse).collect(Collectors.toList());

        LeaveRequestPaginationResponse response = new LeaveRequestPaginationResponse();
        response.setContent(content);
        response.setPageNo(leaveRequestPage.getNumber());
        response.setPageSize(leaveRequestPage.getSize());
        response.setTotalElements(leaveRequestPage.getTotalElements());
        response.setTotalPages(leaveRequestPage.getTotalPages());
        response.setLast(leaveRequestPage.isLast());
        return response;
    }

    public LeaveRequestResponse mapToResponse(LeaveRequest leaveRequest) {
        LeaveRequestResponse leaveRequestResponse = new LeaveRequestResponse();
        leaveRequestResponse.setId(leaveRequest.getId());
        leaveRequestResponse.setStartDate(leaveRequest.getStartDate());
        leaveRequestResponse.setEndDate(leaveRequest.getEndDate());
        leaveRequestResponse.setReason(leaveRequest.getReason());
        leaveRequestResponse.setStatus(leaveRequest.getStatus());
        leaveRequestResponse.setCreatedAt(leaveRequest.getCreatedAt());
        leaveRequestResponse.setUsername(leaveRequest.getUser().getFullName());
        if (leaveRequest.getUser().getManager() == null) {
            leaveRequestResponse.setManager("I am manager");
        } else {
            leaveRequestResponse.setManager(leaveRequest.getUser().getManager().getFullName());
        }
        return leaveRequestResponse;
    }

    public LeaveRequest mapToEntity(LeaveRequestResponse leaveRequestResponse) {
        LeaveRequest leaveRequest = LeaveRequest.builder()
                .id(leaveRequestResponse.getId())
                .startDate(leaveRequestResponse.getStartDate())
                .endDate(leaveRequestResponse.getEndDate())
                .reason(leaveRequestResponse.getReason())
                .status(leaveRequestResponse.getStatus())
                .createdAt(leaveRequestResponse.getCreatedAt())
                .build();
        return leaveRequest;
    }
}
