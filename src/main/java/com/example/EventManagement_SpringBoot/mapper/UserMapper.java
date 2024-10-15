package com.example.EventManagement_SpringBoot.mapper;

import com.example.EventManagement_SpringBoot.dto.request.UserCreationRequest;
import com.example.EventManagement_SpringBoot.dto.response.UserInfoResponse;
import com.example.EventManagement_SpringBoot.dto.request.UserUpdateInfoRequest;
import com.example.EventManagement_SpringBoot.dto.response.UserListResponse;
import com.example.EventManagement_SpringBoot.dto.response.UserQRResponse;
import com.example.EventManagement_SpringBoot.dto.response.UserResponse;
import com.example.EventManagement_SpringBoot.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "gender" ,ignore = true)
    @Mapping(target = "full_Name", ignore = true)
    @Mapping(target = "class_id", ignore = true)
    @Mapping(target = "training_point", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "eventsRegistered", ignore = true)
    @Mapping(target = "id", ignore = true)
    Users toUsers(UserCreationRequest request);

    UserResponse toUserResponse(Users user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "gender" ,source = "gender")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "training_point", ignore = true)
    @Mapping(target = "eventsRegistered", ignore = true)
    void updateUser(@MappingTarget Users user, UserUpdateInfoRequest request);

    UserQRResponse toUserQRResponse(Users user);
    UserListResponse toUserListResponse(Users user);
    UserInfoResponse toUserInfoResponse(Users user);
}