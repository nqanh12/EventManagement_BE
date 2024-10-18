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
    @GetMapping("getInfo/{eventId}")
    ApiResponse<EventResponse> getEventInfoById(@PathVariable String eventId) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.getEventInfoById(eventId))
                .build();
    }

    //Admin cập nhật thông tin sự kiện
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{eventId}")
    ApiResponse<EventResponse> updateEvent(@PathVariable String eventId, @Valid @RequestBody EventUpdateRequest request) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.updateEvent(eventId, request))
                .build();
    }

    //Lấy danh sách người tham gia sự kiện
    @GetMapping("/participants/{eventId}")
    public ApiResponse<ParticipantsResponse> getAll_UserAttendEvent(@PathVariable String eventId) {
        return ApiResponse.<ParticipantsResponse>builder()
                .result(eventService.getAll_UserAttendEvent(eventId))
                .build();
    }


    //Admin xóa sự kiện
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{eventId}")
    public ApiResponse<EventResponse> deleteEvent(@PathVariable String eventId) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.deleteEvent(eventId))
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
    @GetMapping("/getEventName/{eventId}")
    ApiResponse<EventNameResponse> getEventName(@PathVariable String eventId) {
        return ApiResponse.<EventNameResponse>builder()
                .result(eventService.getEventName(eventId))
                .build();
    }

    //Thêm sinh viên vào sự kiện
    @PostMapping("/addParticipant/{eventId}")
    public ApiResponse<EventParticipantResponse> addParticipant(@PathVariable String eventId) {
        return ApiResponse.<EventParticipantResponse>builder()
                .result(eventService.addParticipant(eventId))
                .build();
    }

    //Xóa sinh viên khỏi sự kiện
    @DeleteMapping("/deleteParticipant/{eventId}")
    public ApiResponse<EventParticipantResponse> deleteParticipant(@PathVariable String eventId) {
        return ApiResponse.<EventParticipantResponse>builder()
                .result(eventService.deleteParticipant(eventId))
                .build();
    }

    //Thay đổi trang thái check-in của sinh viên trong document Event
    @PutMapping("/checkIn/{eventId}/{userName}")
    public ApiResponse<EventParticipantResponse> checkIn(@PathVariable String eventId,@PathVariable String userName) {
        return ApiResponse.<EventParticipantResponse>builder()
                .result(eventService.checkInEvent(eventId,userName))
                .build();
    }

    //Thay đổi trang thái check-out của sinh viên trong document Event
    @PutMapping("/checkOut/{eventId}/{userName}")
    public ApiResponse<EventParticipantResponse> checkOut(@PathVariable String eventId,@PathVariable String userName) {
        return ApiResponse.<EventParticipantResponse>builder()
                .result(eventService.checkOutEvent(eventId,userName))
                .build();
    }

    //lấy ra sức chưa của sự kiện
    @GetMapping("/getCapacity/{eventId}")
    public ApiResponse<EventCapacityResponse> getCapacity(@PathVariable String eventId) {
        return ApiResponse.<EventCapacityResponse>builder()
                .result(eventService.getEventCapacity(eventId))
                .build();
    }
}
