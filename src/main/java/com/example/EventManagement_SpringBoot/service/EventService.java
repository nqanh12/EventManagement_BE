package com.example.EventManagement_SpringBoot.service;

import com.example.EventManagement_SpringBoot.dto.request.EventCreationRequest;
import com.example.EventManagement_SpringBoot.dto.request.EventUpdateRequest;

import com.example.EventManagement_SpringBoot.dto.response.*;

import com.example.EventManagement_SpringBoot.entity.Event;

import com.example.EventManagement_SpringBoot.exception.AppException;
import com.example.EventManagement_SpringBoot.exception.ErrorCode;
import com.example.EventManagement_SpringBoot.mapper.EventMapper;
import com.example.EventManagement_SpringBoot.repository.EventRepo;

import com.example.EventManagement_SpringBoot.repository.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class EventService {
    EventRepo eventRepo;
    EventMapper eventMapper;
    UserRepo userRepo;

    //Admin tạo sự kiện
    public EventResponse createEvent(EventCreationRequest request) {
        if (eventRepo.findByEventID(request.getEventID()).isPresent()) {
            log.error("Event already exists with eventID: {}", request.getEventID());
            throw new AppException(ErrorCode.EVENT_EXISTED);
        }
        var event = eventMapper.toEvent(request);
        if (event.getEventID() == null) {
            event.setEventID(generateNextEventID());
        }
        event = eventRepo.save(event);
        return eventMapper.toEventResponse(event);
    }

    //Tạo mã cho sự kiện tiếp theo
    private String generateNextEventID() {
        Optional<Event> lastEvent = eventRepo.findTopByOrderByEventIDDesc();
        String currentYear = String.valueOf(java.time.Year.now().getValue());

        if (lastEvent.isPresent()) {
            String lastEventID = lastEvent.get().getEventID();
            int numericPart = Integer.parseInt(lastEventID.substring(6)); // Adjusted to skip the year and "SK"
            int nextNumericPart = numericPart + 1;

            return currentYear + "SK" + String.format("%03d", nextNumericPart);
        } else {
            return currentYear + "SK001";
        }
    }


    //Admin cập nhật sự kiện
    public EventResponse updateEvent(String eventID,EventUpdateRequest request) {
        var event = eventRepo.findByEventID(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        eventMapper.updateEvent(event, request);
        event = eventRepo.save(event);
        return eventMapper.toEventResponse(event);
    }

    //Xem chi tiết sự kiện
    public EventResponse getEventInfoById(String eventID) {
        Event event = eventRepo.findByEventID(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        return eventMapper.toEventResponse(event);
    }

    //Xem danh sách người tham gia sự kiện
    public ParticipantsResponse getAll_UserAttendEvent(String eventID) {
        Event event = eventRepo.findByEventID(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        return eventMapper.toParticipantsResponse(event);
    }


    //Admin xóa sự kiện
    public EventResponse deleteEvent(String eventID) {
        Event event = eventRepo.findByEventID(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        eventRepo.delete(event);
        return eventMapper.toEventResponse(event);
    }

    //Lấy all sự kiện
    public List<EventListResponse> getAllEvents() {
        List<Event> events = eventRepo.findAll();
        return eventMapper.toListEventResponse(events);
    }

    //Lấy tên sự kiện từ id sự kiện
    public EventNameResponse getEventName(String eventID) {
        var event = eventRepo.findByEventID(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        return EventNameResponse.builder()
                .name(event.getName())
                .build();
    }

    //Thêm sinh viên vào document sự kiện
    public EventParticipantResponse addParticipant(String eventID) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Event event = eventRepo.findByEventID(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));

        // Check if the user exists
        userRepo.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Check if the user is already registered for the event
        boolean alreadyRegistered = event.getParticipants().stream()
                .anyMatch(participant -> participant.getUserName().equals(name));
        if (alreadyRegistered) {
            throw new AppException(ErrorCode.ALREADY_REGISTERED);
        }

        // Create a new participant registration
        Event.Participants participant = Event.Participants.builder()
                .userName(name)
                .checkInStatus(false)
                .checkOutStatus(false)
                .build();

        // Add the participant to the event
        event.getParticipants().add(participant);
        event = eventRepo.save(event);

        return eventMapper.toEventParticipantResponse(event);
    }
}
