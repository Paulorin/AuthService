package com.example.auth_service.controller;

import com.example.auth_service.service.RefreshService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final RefreshService refreshService;

    @PostMapping(path="/refresh", consumes="application/json")
    public ResponseEntity<?> auth(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String refreshToken = cookies[0].getValue();

        System.out.println(refreshToken);

        return refreshService.refresh(refreshToken);
    }

}
