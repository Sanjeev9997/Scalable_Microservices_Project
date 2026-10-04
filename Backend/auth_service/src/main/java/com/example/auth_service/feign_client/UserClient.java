package com.example.auth_service.feign_client;


import com.example.auth_service.dto.UserLoginDto;
import com.example.auth_service.dto.UserRequest;
import com.example.auth_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/login/email/{email}")
    public UserLoginDto getUserByEmailForLogin(@PathVariable String email);

   @PostMapping("/api/users/register")
   public UserResponse createUser(@RequestBody UserRequest userRequest);


}
