package com.example.auth_service.service;

import com.example.auth_service.controller.dto.UserInfo;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    Boolean authenticateUser (UserInfo userInfo);
    ResponseCookie generateAccessCookie(String name, String value, String path);
    ResponseCookie generateRefreshCookie(String name, String value, String path);
    ResponseEntity<?> generateResponse(String username);
}
