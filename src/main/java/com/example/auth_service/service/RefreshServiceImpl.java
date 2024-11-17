package com.example.auth_service.service;

import com.example.auth_service.persistence.repository.UserRepository;
import com.example.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;


    @Override
    public void verifyRefreshToken(String refreshToken) {
        jwtUtil.verifyRefreshToken(refreshToken);
    }

    @Override
    public ResponseEntity<?> refresh(String refreshToken) {
        if (!jwtUtil.verifyRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Forbidden!");
        }

        String username = jwtUtil.getUsernameFromJwt(refreshToken);

        ResponseCookie accessCookie = generateAccessCookie("Access-token",
                jwtUtil.generateAccessJwt(username), "/");

        ResponseCookie refreshCookie = generateRefreshCookie("Refresh-token",
                jwtUtil.generateRefreshJwt(username), "/refresh");

        System.currentTimeMillis();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("User authenticated!");
    }

    public ResponseCookie generateAccessCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value)
                .path(path)
                .maxAge(60 * 60)
                .httpOnly(true).build();
    }

    public ResponseCookie generateRefreshCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value)
                .path(path)
                .maxAge(60 * 60 * 24)
                .httpOnly(true).build();
    }
}
