package com.example.auth_service.controller;

import com.example.auth_service.dto.JwtResponse;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.UserRequest;
import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody UserRequest request) {
        return new ResponseEntity<>(
                authenticationService.register(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authenticationService.login(request)
        );
    }
}
