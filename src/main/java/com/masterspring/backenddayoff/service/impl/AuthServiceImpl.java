package com.masterspring.backenddayoff.service.impl;

import com.masterspring.backenddayoff.dto.request.AuthRequest;
import com.masterspring.backenddayoff.dto.response.AuthResponse;
import com.masterspring.backenddayoff.entity.LeaveRemain;
import com.masterspring.backenddayoff.mapper.AuthResponseMapper;
import com.masterspring.backenddayoff.repository.LeaveRemainRepository;
import com.masterspring.backenddayoff.repository.UserRepository;
import com.masterspring.backenddayoff.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final LeaveRemainRepository leaveRemainRepository;
    private final AuthResponseMapper authResponseMapper;

    public AuthServiceImpl(UserRepository userRepository, LeaveRemainRepository leaveRemainRepository, AuthResponseMapper authResponseMapper) {
        this.userRepository = userRepository;
        this.leaveRemainRepository = leaveRemainRepository;
        this.authResponseMapper = authResponseMapper;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        // Find user by email and password.
        var user = userRepository.findByEmailAndPassword(authRequest.getEmail(), authRequest.getPassword());

        // User is null then throw error.
        if (user == null) throw new RuntimeException("Invalid email or password");

        // Create AuthResponse mapped from user.
        var authResponse = authResponseMapper.fromUser(user);

        // Check remain days.
        checkRemainDays(user.getLeaveRemain());

        return authResponse;
    }

    /**
     * This method check whether if this is the next year, then update the {@code LeaveRemain} table.
     *
     * @param leaveRemain the previous LeaveRemain.
     */
    private void checkRemainDays(LeaveRemain leaveRemain) {
        if (LocalDateTime.now().getYear() > leaveRemain.getYear()) {
            leaveRemainRepository.findById(leaveRemain.getId()).ifPresent(remain -> {
                remain.setYear(remain.getYear() + 1);
                remain.setRemainDays(remain.getRemainDays() + 1);
            });
        }
    }
}
