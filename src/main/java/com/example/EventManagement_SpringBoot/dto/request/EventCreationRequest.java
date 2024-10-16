package com.example.EventManagement_SpringBoot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCreationRequest {
     String id; // Event ID
     String eventId;

    @NotBlank(message = "Không được bỏ trống tên")
     String name; // Tên sự kiện

    @NotBlank(message = "Không được bỏ trống sức chứa")
     String capacity;

    @NotBlank(message = "Không được bỏ trống mô tả sự kiện")
     String description; // Mô tả sự kiện

    @NotBlank(message = "Không được bỏ trống địa điểm")
     String locationId; // ID của địa điểm

    @NotNull(message = "Vui lòng điền ngày bắt đầu")
     Date dateStart; // Ngày bắt đầu

    @NotNull(message = "Vui lòng điền ngày kết thúc")
     Date dateEnd;

    @NotBlank(message = "Không được bỏ trống manager")
     String managerName;

}