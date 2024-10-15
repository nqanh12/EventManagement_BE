package com.example.EventManagement_SpringBoot.controller;

import com.example.EventManagement_SpringBoot.dto.request.*;
import com.example.EventManagement_SpringBoot.dto.response.*;
import com.example.EventManagement_SpringBoot.service.EventService;
import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
     EventService eventService;

     //Admin tạo sự kiện
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createEvent")
    ApiResponse<EventResponse> createEvent(@Valid @RequestBody EventCreationRequest request) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.createEvent(request))
                .build();
    }

    //Xem chi tiết sự kiện
    @GetMapping("getInfo/{eventID}")
    ApiResponse<EventResponse> getEventInfoById(@PathVariable String eventID) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.getEventInfoById(eventID))
                .build();
    }

    //Admin cập nhật thông tin sự kiện
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{eventID}")
    ApiResponse<EventResponse> updateEvent(@PathVariable String eventID, @Valid @RequestBody EventUpdateRequest request) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.updateEvent(eventID, request))
                .build();
    }

    //Lấy danh sách người tham gia sự kiện
    @GetMapping("/participants/{eventID}")
    public ApiResponse<ParticipantsResponse> getAll_UserAttendEvent(@PathVariable String eventID) {
        return ApiResponse.<ParticipantsResponse>builder()
                .result(eventService.getAll_UserAttendEvent(eventID))
                .build();
    }


    //Admin xóa sự kiện
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{eventID}")
    public ApiResponse<EventResponse> deleteEvent(@PathVariable String eventID) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.deleteEvent(eventID))
                .build();
    }

    //Lấy danh sách sự kiện
        @GetMapping("/listEvent")
    public ApiResponse<List<EventListResponse>> getEvents() {
        return ApiResponse.<List<EventListResponse>>builder()
                .result(eventService.getAllEvents())
                .build();
    }

    //Lấy tên sự kiện từ id sự kiện
    @GetMapping("/getEventName/{eventID}")
    ApiResponse<EventNameResponse> getEventName(@PathVariable String eventID) {
        return ApiResponse.<EventNameResponse>builder()
                .result(eventService.getEventName(eventID))
                .build();
    }

    //Thêm sinh viên vào sự kiện
    @PostMapping("/addParticipant/{eventID}")
    public ApiResponse<EventParticipantResponse> addParticipant(@PathVariable String eventID) {
        return ApiResponse.<EventParticipantResponse>builder()
                .result(eventService.addParticipant(eventID))
                .build();
    }
}
