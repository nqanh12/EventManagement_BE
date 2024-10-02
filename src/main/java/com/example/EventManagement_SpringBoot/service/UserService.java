package com.example.EventManagement_SpringBoot.service;

import com.example.EventManagement_SpringBoot.dto.request.ApiResponse;
import com.example.EventManagement_SpringBoot.dto.request.UserCreationRequest;
import com.example.EventManagement_SpringBoot.dto.response.UserResponse;
import com.example.EventManagement_SpringBoot.entity.Users;
import com.example.EventManagement_SpringBoot.enums.Role;
import com.example.EventManagement_SpringBoot.exception.AppException;
import com.example.EventManagement_SpringBoot.exception.ErrorCode;
import com.example.EventManagement_SpringBoot.mapper.UserMapper;
import com.example.EventManagement_SpringBoot.repository.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
    UserRepo userRepo;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse register(UserCreationRequest request) {
        var user = userMapper.toUsers(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(new HashSet<>(List.of(Role.USER.name())));
        try {
            user = userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("User already exists", e);
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponseApiResponse(user).getResult();
    }

    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::toUserResponseApiResponse)
                .map(ApiResponse::getResult)
                .collect(Collectors.toList());
    }

    public UserResponse getUser(String userId) {
        var user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponseApiResponse(user).getResult();
    }

    public UserResponse getMyInfo() {
        var user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.toUserResponseApiResponse(user).getResult();
    }


}