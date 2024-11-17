package com.example.auth_service.service;

import com.example.auth_service.controller.dto.UserInfo;

public interface SignUpService {
    UserInfo saveUser(UserInfo userInfo);
    Boolean existsByUsername(String username);
}
