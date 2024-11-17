package com.example.auth_service.controller;

import com.example.auth_service.controller.dto.UserInfo;
import com.example.auth_service.service.SignUpServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpServiceImpl signUpService;

    @PostMapping(path="/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserInfo userInfo, HttpServletRequest request) {
        if (signUpService.existsByUsername(userInfo.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        signUpService.saveUser(userInfo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}