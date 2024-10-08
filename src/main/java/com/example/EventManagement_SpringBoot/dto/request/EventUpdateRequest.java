package com.example.EventManagement_SpringBoot.dto.request;

import com.example.EventManagement_SpringBoot.entity.Event;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequest {
    String name; // Tên sự kiện
    String description; // Mô tả sự kiện
    String locationId; // ID của địa điểm
    Date dateStart; // Ngày bắt đầu
    Date dateEnd; // Ngày kết thúc
    List<Event.Participant> participants; // Danh sách người tham gia
    String managerId;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Participant {
        String userId; // ID của người dùng tham gia
        boolean checkInStatus; // Trạng thái check-in
        boolean checkOutStatus; // Trạng thái check-out
    }
}
