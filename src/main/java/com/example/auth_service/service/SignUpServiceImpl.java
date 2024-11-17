package com.example.auth_service.service;

import com.example.auth_service.controller.dto.UserInfo;
import com.example.auth_service.persistence.entity.UserEntity;
import com.example.auth_service.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Override
    public UserInfo saveUser(UserInfo userInfo) {
        UserEntity user = new UserEntity(
                null,
                userInfo.getUsername(),
                userInfo.getPassword()
        );

        /**
         * Encoding password with BcryptPassworEncoder before saving in to database
         */
        user.setPassword(encoder.encode(user.getPassword()));
        UserEntity createdUser = userRepository.save(user);

        return new UserInfo(
                createdUser.getUsername(),
                createdUser.getPassword()
        );
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
