package com.example.EventManagement_SpringBoot.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@JsonPropertyOrder({
        "id", "name", "description", "locationId", "dateStart", "dateEnd",
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
    String eventID;
    String name; // Tên sự kiện
    String description; // Mô tả sự kiện
    String locationId; // ID của địa điểm
    Date dateStart; // Ngày bắt đầu
    Date dateEnd; // Ngày kết thúc
    String managerName;
}
