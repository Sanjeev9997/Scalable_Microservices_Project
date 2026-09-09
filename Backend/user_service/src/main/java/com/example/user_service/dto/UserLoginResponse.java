package com.example.user_service.dto;

import com.example.user_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private Long id;
    private String email;
    private String password;
    private Role role;
}
