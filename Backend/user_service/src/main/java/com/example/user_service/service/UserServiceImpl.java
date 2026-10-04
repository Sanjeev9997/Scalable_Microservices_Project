package com.example.user_service.service;

import com.example.user_service.dto.*;
import com.example.user_service.model.Address;
import com.example.user_service.model.User;
import com.example.user_service.repository.AddressRepository;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserResponse createUser(UserRequest request) {
        logger.info("createUser called");
        User user=User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .gender(request.getGender())
                .password(request.getPassword())
                .build();
        User savedUser=userRepository.save(user);
        logger.info("createUser completed savedUser");
        return mapToresponse(savedUser);
    }

    @Cacheable(value = "users",key = "#id")
    @Override
    public UserResponse getUser(Long id) {
        logger.info("getUser called");
        logger.info("user fetching");
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        logger.info("user fetched");
        List<Address> addresses=addressRepository.findByUserId(id);
        user.setAddresses(addresses);
        logger.info("getUser completed");
        return mapToresponse(user);
    }

    @Cacheable(value = "users",key = "#email")
    @Override
    public UserLoginResponse loginUser(String email) {
        logger.info("loginUser called");
        logger.info("user fetching vai email.");
        User user=userRepository.findByEmail(email);
        logger.info("user fetched via email.");
        logger.info("loginUser completed");
        return UserLoginResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .id(user.getId())
                .password(user.getPassword())
                .build();

    }
    @Cacheable(value = "users",key = "#email")
    @Override
    public UserResponse getUserByEmail(String email){
        logger.info("Fetch by email called");
        logger.info("user fetching vai email.");
        User user=userRepository.findByEmail(email);
        logger.info("user fetched via email.");
        logger.info("Fetch by completed email.");
        return mapToresponse(user);
    }
    @Override
    public List<UserResponse> getAllUsers() {
        logger.info("getAllUsers called");
        List<UserResponse> users=userRepository.findAll().stream().map(this::mapToresponse).toList();
        logger.info("getAllUsers completed");
        return users;

    }
    @CachePut(value = "users",key = "#id")
    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        logger.info("updateUser called");
        logger.info("user fetching");
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        logger.info("user fetched");
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        User savedUser=userRepository.save(user);
        logger.info("updateUser completed");
        return mapToresponse(savedUser);
    }

    @CacheEvict(value = "users",key="#id")
    @Override
    public void deleteUser(Long id) {
        logger.info("deleteUser called");
        logger.info("user fetching");
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        logger.info("user fetched");
        userRepository.delete(user);
        logger.info("deleteUser completed");
    }

    private UserResponse mapToresponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .gender(user.getGender())
                .build();
    }
}
