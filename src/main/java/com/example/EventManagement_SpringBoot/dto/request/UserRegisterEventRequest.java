package com.example.EventManagement_SpringBoot.dto.request;

import com.example.EventManagement_SpringBoot.entity.Users;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterEventRequest {
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
