package com.example.EventManagement_SpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Date;
import java.util.Set;

@Document(collection = "Users")
@JsonPropertyOrder({
        "id","eventId", "userName", "password", "full_Name", "gender", "class_id", "training_point",
        "email", "phone", "address", "role", "eventsRegistered"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users {
     @Id
     String id;
     String userName;
     String password;
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
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EventRegistration {
         String eventId;
         String name; // Tên sự kiện
         Date registrationDate;
         String qrCode;
         boolean checkInStatus;
         Date checkInTime;
         boolean checkOutStatus;
         Date checkOutTime;
    }

}