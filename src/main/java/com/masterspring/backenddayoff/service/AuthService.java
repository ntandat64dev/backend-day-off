package com.masterspring.backenddayoff.service;

import com.masterspring.backenddayoff.dto.request.AuthRequest;
import com.masterspring.backenddayoff.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}
