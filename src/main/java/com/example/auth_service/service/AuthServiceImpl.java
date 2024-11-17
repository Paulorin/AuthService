package com.example.auth_service.service;

import com.example.auth_service.controller.dto.UserInfo;
import com.example.auth_service.persistence.repository.UserRepository;
import com.example.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtil jwtUtil;

    @Override
    public Boolean authenticateUser(UserInfo userInfo) {
        boolean match = encoder.matches(
                userInfo.getPassword(),
                userRepository.findByUsername(userInfo.getUsername()).getPassword()
        );

        return match;
    }

    @Override
    public ResponseCookie generateAccessCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value)
                .path(path)
                .maxAge(60 * 60)
                .httpOnly(true).build();
    }

    @Override
    public ResponseCookie generateRefreshCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value)
                .path(path)
                .maxAge(60 * 60 * 24)
                .httpOnly(true).build();
    }

    @Override
    public ResponseEntity<?> generateResponse(String userName) {
        ResponseCookie accessCookie = generateAccessCookie("Acces-token",
                jwtUtil.generateAccessJwt(userName), "/");

        ResponseCookie refreshCookie = generateRefreshCookie("Refresh-token",
                jwtUtil.generateRefreshJwt(userName), "/refresh");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("User authenticated!");
    }

}
