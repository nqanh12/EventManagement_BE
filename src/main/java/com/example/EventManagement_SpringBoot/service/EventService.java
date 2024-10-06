package com.example.EventManagement_SpringBoot.service;

import com.example.EventManagement_SpringBoot.dto.request.EventCreationRequest;
import com.example.EventManagement_SpringBoot.dto.response.EventResponse;
import com.example.EventManagement_SpringBoot.entity.Event;
import com.example.EventManagement_SpringBoot.exception.AppException;
import com.example.EventManagement_SpringBoot.exception.ErrorCode;
import com.example.EventManagement_SpringBoot.repository.EventRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class EventService {
    EventRepo eventRepo;

    //admin tạo sự kiện
    public EventResponse createEvent(EventCreationRequest request) {
        if (eventRepo.findByEventID(request.getEventID()).isPresent()) {
            log.error("Event already exists with eventID: {}", request.getEventID());
            throw new AppException(ErrorCode.EVENT_EXISTED);
        }
        var event = Event.builder()
                .eventID(generateNextEventID())
                .name(request.getName())
                .description(request.getDescription())
                .locationId(request.getLocationId())
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .managerId(request.getManagerId())
                .build();
        event = eventRepo.save(event);
        return EventResponse.builder()
                .eventID(event.getEventID())
                .name(event.getName())
                .description(event.getDescription())
                .locationId(event.getLocationId())
                .dateStart(event.getDateStart())
                .dateEnd(event.getDateEnd())
                .managerId(event.getManagerId())
                .build();
    }

    //Tạo ID cho sự kiện
    private String generateNextEventID() {
        Optional<Event> lastEvent = eventRepo.findTopByOrderByEventIDDesc();
        if (lastEvent.isPresent()) {
            String lastEventID = lastEvent.get().getEventID();
            int numericPart = Integer.parseInt(lastEventID.substring(2));
            int nextNumericPart = numericPart + 1;
            return "SK" + String.format("%03d", nextNumericPart);
        } else {
            return "SK001";
        }
    }

}
