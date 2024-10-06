package com.example.EventManagement_SpringBoot.mapper;

import com.example.EventManagement_SpringBoot.dto.request.ApiResponse;
import com.example.EventManagement_SpringBoot.dto.request.UserCreationRequest;
import com.example.EventManagement_SpringBoot.dto.request.UserUpdateRequest;
import com.example.EventManagement_SpringBoot.dto.response.UserResponse;
import com.example.EventManagement_SpringBoot.entity.Role;
import com.example.EventManagement_SpringBoot.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Users toUsers(UserCreationRequest request);
    UserResponse toUserResponse(Users user);
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
}
