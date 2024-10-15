package com.example.EventManagement_SpringBoot.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {
    String userName;
    String full_Name;
    String gender;
    String class_id;
    String training_point;
    String email;
    String phone;
    String address;
}
