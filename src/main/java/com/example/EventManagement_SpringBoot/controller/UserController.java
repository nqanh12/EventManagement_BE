package com.example.EventManagement_SpringBoot.controller;

import com.example.EventManagement_SpringBoot.dto.request.*;
import com.example.EventManagement_SpringBoot.dto.response.*;
import com.example.EventManagement_SpringBoot.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    UserService userService;


    //Sinh viện đăng kí tài khoản người dùng
    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.register(request))
                .build();
    }

    //Admin lấy danh sách người dùng
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listUsers")
    ApiResponse<List<UserListResponse>> getUsers() {
        return ApiResponse.<List<UserListResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    //Sinh viên cập nhật thông tin cá nhân
    @PutMapping("/updateInfo")
    ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateInfoRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(request))
                .build();
    }

    //Sinh viên xem thông tin cá nhân
    @GetMapping("/myInfo")
    ApiResponse<UserInfoResponse> getMyInfo() {
        return ApiResponse.<UserInfoResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    //Admin xóa tài khoản sinh viên
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{userName}")
    ApiResponse<UserResponse> deleteUser(@PathVariable String userName) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.deleteUser(userName))
                .build();
    }

    //Admin chỉnh sửa role của sinh viên
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateRole/{userName}")
    ApiResponse<UserResponse> updateRole(@PathVariable String userName, @RequestBody @Valid UserUpdateRoleRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUserRole(userName, request))
                .build();
    }

    //Sinh viên đăng kí sự kiện
    @PostMapping("/registerEvent/{eventID}")
    ApiResponse<UserResponse> registerEvent(@PathVariable String eventID) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.registerEvent(eventID))
                .build();
    }

    // Sinh viên lấy mã QR từ id sự kiện
    @GetMapping("/getQRCode/{eventID}")
    ApiResponse<UserQRResponse> getQRCode(@PathVariable String eventID) {
        return ApiResponse.<UserQRResponse>builder()
                .result(userService.getQRCode(eventID))
                .build();
    }

    //Sinh viên xem danh sách sự kiên đã đăng kí
    @GetMapping("/getRegisteredEvents")
    ApiResponse<UserQRResponse> getRegisteredEvents() {
        return ApiResponse.<UserQRResponse>builder()
                .result(userService.getRegisteredEvents())
                .build();
    }

    //Lấy full_Name từ UserName
    @GetMapping("/getFullName/{userName}")
    ApiResponse<UserNameResponse> getFullName(@PathVariable String userName) {
        return ApiResponse.<UserNameResponse>builder()
                .result(userService.getFullName(userName))
                .build();
    }

}
