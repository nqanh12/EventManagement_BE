package com.example.EventManagement_SpringBoot.service;

import com.example.EventManagement_SpringBoot.dto.request.*;
import com.example.EventManagement_SpringBoot.dto.response.*;

import com.example.EventManagement_SpringBoot.entity.Users;
import com.example.EventManagement_SpringBoot.enums.Role;
import com.example.EventManagement_SpringBoot.exception.AppException;
import com.example.EventManagement_SpringBoot.exception.ErrorCode;
import com.example.EventManagement_SpringBoot.mapper.UserMapper;
import com.example.EventManagement_SpringBoot.repository.EventRepo;
import com.example.EventManagement_SpringBoot.repository.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
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
    EventRepo eventRepo;

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


    //Admin xem danh sách người dùng
    public List<UserListResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::toUserListResponse)
                .collect(Collectors.toList());
    }


    //User lấy thông tin cá nhân
    public UserInfoResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();


        Users user = userRepo.findByUserName(name)
                .orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserInfoResponse(user);
    }


    //User cập nhật thông tin người dùng
    public UserResponse updateUser(UserUpdateInfoRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        var user = userRepo.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        user = userRepo.save(user);
        return userMapper.toUserResponse(user);
    }


    //Admin xóa người dùng
    public UserResponse deleteUser(String userName) {
        var user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepo.delete(user);
        return userMapper.toUserResponse(user);
    }

    //Admin cập nhật role của sinh viên
    public UserResponse updateUserRole(String userName, UserUpdateRoleRequest request) {
        var user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setRoles(request.getRoles());
        user = userRepo.save(user);
        return userMapper.toUserResponse(user);
    }

    //Sinh viên đăng kí sự kiên và tạo mã QR
    public UserResponse registerEvent(String eventID) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        var user = userRepo.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        //Kiểm tra sự kiện có tồn tại hay không
        var event = eventRepo.findByEventID(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        //Kiểm tra sự kiện có tồn tại không
        if (user.getEventsRegistered() == null) {
            user.setEventsRegistered(new ArrayList<>());
        }
        //Kiểm tra sự kiển đã đăng kí chưa
        boolean alreadyRegistered = user.getEventsRegistered().stream()
                .anyMatch(registration -> registration.getEventId().equals(eventID));
        if (alreadyRegistered) {
            throw new AppException(ErrorCode.ALREADY_REGISTERED);
        }
        //Tạo thông tin đăng kí sự kiện
        Users.EventRegistration eventRegistration = Users.EventRegistration.builder()
                .eventId(eventID)
                .registrationDate(new Date())
                .qrCode(generateQRCode(eventID, name)) // Assuming you have a method to generate QR code
                .checkInStatus(false)
                .checkOutStatus(false)
                .build();

        //Lưu thông tin đăng kí sự kiện
        user.getEventsRegistered().add(eventRegistration);
        user = userRepo.save(user);

        return userMapper.toUserResponse(user);
    }

    //Tạo mã QR
    private String generateQRCode(String eventID, String userName) {
        return eventID + userName;
    }

    //Sinh viên lấy mã QR từ id sự kiện
    public UserQRResponse getQRCode(String eventID) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Users user = userRepo.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var eventRegistration = user.getEventsRegistered().stream()
                .filter(registration -> registration.getEventId().equals(eventID))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        UserQRResponse.EventRegistration responseEvent = new UserQRResponse.EventRegistration(
                eventRegistration.getEventId(),
                eventRegistration.getName(),
                eventRegistration.getRegistrationDate(),
                eventRegistration.getQrCode(),
                eventRegistration.isCheckInStatus(),
                eventRegistration.getCheckInTime(),
                eventRegistration.isCheckOutStatus(),
                eventRegistration.getCheckOutTime()
        );
        UserQRResponse userQRResponse = new UserQRResponse(List.of(responseEvent));
        return userQRResponse;
    }


    //Sinh viên xem danh sách sự kiện đã đăng kí
    public UserQRResponse getRegisteredEvents() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Users user = userRepo.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserQRResponse(user);
    }

    //Lấy full_Name từ userName
    public UserNameResponse getFullName(String userName) {
        var user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return UserNameResponse.builder()
                .userName(userName)
                .class_id(user.getClass_id())
                .full_Name(user.getFull_Name())
                .build();
    }
}