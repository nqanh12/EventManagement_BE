package com.example.EventManagement_SpringBoot.controller;

import com.example.EventManagement_SpringBoot.dto.request.EventCreationRequest;
import com.example.EventManagement_SpringBoot.entity.Event;
import com.example.EventManagement_SpringBoot.service.EventService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {
    private EventService eventService;

    @PostMapping("/createEvent")
    public Event createUser(@RequestBody @Valid EventCreationRequest request) {
        return eventService.createEvent(request);
    }
}
