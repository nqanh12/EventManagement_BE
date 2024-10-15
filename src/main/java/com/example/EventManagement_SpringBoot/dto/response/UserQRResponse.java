package com.example.EventManagement_SpringBoot.dto.response;

import com.example.EventManagement_SpringBoot.entity.Users;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@JsonPropertyOrder({
        "eventId","name", "registrationDate", "qrCode", "checkInStatus", "checkInTime", "checkOutStatus", "checkOutTime"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserQRResponse {
    List<EventRegistration> eventsRegistered;
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
