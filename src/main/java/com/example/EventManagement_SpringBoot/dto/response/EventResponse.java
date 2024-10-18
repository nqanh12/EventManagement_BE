package com.example.EventManagement_SpringBoot.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@JsonPropertyOrder({
        "id","eventId", "name","capacity","description", "locationId", "dateStart", "dateEnd",
        "participants", "ManagerName"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventResponse {
    @Id
    String id; // Event ID
    String eventId;
    String name; // Tên sự kiện
    int capacity;
    String description; // Mô tả sự kiện
    String locationId; // ID của địa điểm
    Date dateStart; // Ngày bắt đầu
    Date dateEnd; // Ngày kết thúc
    List<Participant> participants; // Danh sách người tham gia
    String managerName;


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

