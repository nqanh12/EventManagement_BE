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
        "id", "userName", "password", "full_Name", "class_id", "training_point",
        "email", "phone", "address", "role", "eventsRegistered"
})
@Getter
@Setter
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
     String class_id;
     String training_point;
     String email;
     String phone;
     String address;
     Set<String> roles;
     List<EventRegistration> eventsRegistered;

    @Setter
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EventRegistration {
         String eventId;
         Date registrationDate;
         String qrCode;
         boolean checkInStatus;
         boolean checkOutStatus;
    }

}