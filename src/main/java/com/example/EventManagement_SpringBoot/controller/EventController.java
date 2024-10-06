package com.example.EventManagement_SpringBoot.controller;

import com.example.EventManagement_SpringBoot.dto.request.ApiResponse;
import com.example.EventManagement_SpringBoot.dto.request.EventCreationRequest;
import com.example.EventManagement_SpringBoot.dto.response.EventResponse;
import com.example.EventManagement_SpringBoot.service.EventService;
import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventController {
     EventService eventService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createEvent")
    ApiResponse<EventResponse> createEvent(@Valid @RequestBody EventCreationRequest request) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.createEvent(request))
                .build();
    }
}
