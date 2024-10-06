package com.example.EventManagement_SpringBoot.dto.request;

import com.example.EventManagement_SpringBoot.entity.Users;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     String userName;
     String password;
     String full_Name;
     String class_id;
     String training_point;
     String email;
     String phone;
     String address;
    Set<String> roles;
     List<Users.EventRegistration> eventsRegistered;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EventRegistration {
         String eventId;
         Date registrationDate;
         String qrCode;
         boolean checkInStatus;
         boolean checkOutStatus;
    }
}
