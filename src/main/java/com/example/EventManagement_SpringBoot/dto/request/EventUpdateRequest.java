package com.example.EventManagement_SpringBoot.dto.request;

import com.example.EventManagement_SpringBoot.entity.Event;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequest {
    String name; // Tên sự kiện
    int capacity;
    String description; // Mô tả sự kiện
    String locationId; // ID của địa điểm
    Date dateStart; // Ngày bắt đầu
    Date dateEnd; // Ngày kết thúc
    String managerName;
}
