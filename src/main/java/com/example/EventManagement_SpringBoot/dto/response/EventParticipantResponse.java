package com.example.EventManagement_SpringBoot.dto.response;

import com.example.EventManagement_SpringBoot.entity.Event;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventParticipantResponse {
    List<Event.Participants> participants; // Danh sách người tham gia

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Participant {
        String userName; // ID của người dùng tham gia
        boolean checkInStatus; // Trạng thái check-in
        Date checkInTime;
        boolean checkOutStatus;
        Date checkOutTime;
    }
}
