package com.example.EventManagement_SpringBoot.service;

import com.example.EventManagement_SpringBoot.dto.request.UserCreationRequest;
import com.example.EventManagement_SpringBoot.dto.request.UserUpdateRequest;
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

    //Đăng kí tài khoản
    public UserResponse register(UserCreationRequest request) {
        if (userRepo.findByUserName(request.getUserName()).isPresent()) {
            log.error("User already exists with username: {}", request.getUserName());
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        var user = userMapper.toUsers(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(new HashSet<>(List.of(Role.USER.name())));
        try {
            user = userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation", e);
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }


    //Admin lấy danh sách người dùng
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }


    //User lấy thông tin cá nhân
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();


        Users user = userRepo.findByUserName(name)
                 .orElseThrow(
                         ()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }


    //User cập nhật thông tin người dùng
    public UserResponse updateUser(String userName,UserUpdateRequest request){
        var user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            user.setRoles(request.getRoles());
        }
        user = userRepo.save(user);
        return userMapper.toUserResponse(user);
    }


    //Admin xóa người dùng
    public UserResponse deleteUser(String userName){
        var user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepo.delete(user);
        return userMapper.toUserResponse(user);
    }
}