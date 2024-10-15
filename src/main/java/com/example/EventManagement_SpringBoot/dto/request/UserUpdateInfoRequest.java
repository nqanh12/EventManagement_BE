package com.example.EventManagement_SpringBoot.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateInfoRequest {
    String full_Name;
    String gender;
    String class_id;
    String email;
    String phone;
    String address;
}
