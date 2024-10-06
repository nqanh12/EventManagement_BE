package com.example.EventManagement_SpringBoot.controller;

import com.example.EventManagement_SpringBoot.dto.request.ApiResponse;
import com.example.EventManagement_SpringBoot.dto.request.UserCreationRequest;
import com.example.EventManagement_SpringBoot.dto.request.UserUpdateRequest;
import com.example.EventManagement_SpringBoot.dto.response.UserResponse;
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
@RequiredArgsConstructor
public class UserController {
    UserService userService;


    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.register(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateUser/{userName}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userName, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userName, request))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{userName}")
    ApiResponse<UserResponse> deleteUser(@PathVariable String userName) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.deleteUser(userName))
                .build();
    }
}
