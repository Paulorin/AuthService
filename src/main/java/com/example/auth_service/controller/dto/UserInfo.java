package com.example.auth_service.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
        @NotBlank(message = "Username should be between 3 and 20 characters.") @Size(min = 3, max = 20)
        private String username;
        @NotBlank(message = "Password should be between 6 and 40 characters.") @Size(min = 6, max = 40)
        private String password;
}
