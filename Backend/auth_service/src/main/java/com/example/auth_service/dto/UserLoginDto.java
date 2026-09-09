package com.example.auth_service.dto;

import com.example.auth_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    private Long id;
    private String email;
    private String password;
    private Role role;
}
