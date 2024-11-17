package com.example.auth_service.controller;

import com.example.auth_service.controller.dto.UserInfo;
import com.example.auth_service.service.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping(path="/auth", consumes = "application/json")
    public ResponseEntity<?> auth(@RequestBody UserInfo userInfo, HttpServletRequest request) {

        Boolean match = authService.authenticateUser(userInfo);

        if (!match) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ResponseEntity<?> response = authService.generateResponse(userInfo.getUsername());

        System.currentTimeMillis();
        return response;
    }

}
