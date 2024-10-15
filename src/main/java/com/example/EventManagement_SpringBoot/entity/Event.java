package com.example.EventManagement_SpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "Events")
@JsonPropertyOrder({
        "id", "name", "description", "locationId", "dateStart", "dateEnd",
        "participants", "managerName"
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
     String id; // Event ID
     String eventID;
     String name; // Tên sự kiện
     String description; // Mô tả sự kiện
     String locationId; // ID của địa điểm
     Date dateStart; // Ngày bắt đầu
     Date dateEnd; // Ngày kết thúc
     String managerName;
     List<Event.Participants> participants; // Danh sách người tham gia

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Participants {
         String userName; // ID của người dùng tham gia
         boolean checkInStatus; // Trạng thái check-in
         Date checkInTime;
         boolean checkOutStatus; // Trạng thái check-out
         Date checkOutTime; // Thời gian check-out
    }

}