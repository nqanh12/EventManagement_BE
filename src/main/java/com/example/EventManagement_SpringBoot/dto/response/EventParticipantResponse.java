package com.example.EventManagement_SpringBoot.dto.response;

import com.example.EventManagement_SpringBoot.entity.Event;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventParticipantResponse {
    List<Event.Participants> participants; // Danh sách người tham gia
}
