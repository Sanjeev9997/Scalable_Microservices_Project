package com.example.user_service.service;


import com.example.user_service.dto.UserFetchDto;
import com.example.user_service.dto.UserLoginResponse;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.model.User;

import java.util.List;


public interface UserService
{

    UserResponse createUser(UserRequest request);

    UserResponse getUser(Long id);

    UserLoginResponse loginUser(String email);
    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
