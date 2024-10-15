package com.example.EventManagement_SpringBoot.dto.response;

import com.example.EventManagement_SpringBoot.entity.Users;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Set;


@JsonPropertyOrder({
        "id", "userName", "password", "full_Name", "gender", "class_id", "training_point",
        "email", "phone", "address", "role", "eventsRegistered"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String userName;
    String full_Name;
    String gender;
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
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EventRegistration {
        String eventId;
        String name;
        Date registrationDate;
        String qrCode;
        boolean checkInStatus;
        Date checkInTime;
        boolean checkOutStatus;
        Date checkOutTime;
    }
}
