package com.example.EventManagement_SpringBoot.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.util.Date;

@JsonPropertyOrder({
        "id", "eventId","name", "capacity","description", "locationId", "dateStart", "dateEnd",
        "participants", "ManagerName"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventListResponse {
    @Id
    String id; // Event ID
    String eventId;
    String name; // Tên sự kiện
    String capacity;
    String description; // Mô tả sự kiện
    String locationId; // ID của địa điểm
    Date dateStart; // Ngày bắt đầu
    Date dateEnd; // Ngày kết thúc
    String managerName;
}
