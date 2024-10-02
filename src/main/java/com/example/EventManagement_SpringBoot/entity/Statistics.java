package com.example.EventManagement_SpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Statistics")
@JsonPropertyOrder({
        "id", "eventId", "totalParticipants", "checkedIn", "checkedOut", "timestamp"
})
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Statistics {
    @Id
     String id; // Statistics ID
     String eventId; // ID của sự kiện
     int totalParticipants; // Tổng số người tham gia
     int checkedIn; // Số lượng người đã check-in
     int checkedOut; // Số lượng người đã check-out
     Date timestamp; // Thời gian tạo thống kê
}