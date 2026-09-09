package com.example.auth_service.service;


import com.example.auth_service.dto.*;
import com.example.auth_service.feign_client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResponse register(UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        System.out.println("Request sent to userRequest");
        return userClient.createUser(userRequest);
    }

    public JwtResponse login(LoginRequest request){
        UserLoginDto user=userClient.getUserByEmail(request.getEmail());
        if(user==null){
            throw new RuntimeException("Invalid email or password");

        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token=jwtService.generateToken(user.getEmail(),user.getRole());
        JwtResponse jwtResponse=new JwtResponse();
        jwtResponse.setToken(token);
        return jwtResponse;
    }
}
