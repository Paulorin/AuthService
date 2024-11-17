package com.example.auth_service.service;

import org.springframework.http.ResponseEntity;

public interface RefreshService {
    void verifyRefreshToken(String refreshToken);
    ResponseEntity<?> refresh(String refreshToken);
}
