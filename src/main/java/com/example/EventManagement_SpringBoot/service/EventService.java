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

import java.util.Date;
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
        if (eventRepo.findByEventId(request.getEventId()).isPresent()) {
            log.error("Event already exists with eventID: {}", request.getEventId());
            throw new AppException(ErrorCode.EVENT_EXISTED);
        }
        var event = eventMapper.toEvent(request);
        if (event.getEventId() == null) {
            event.setEventId(generateNextEventID());
        }
        event = eventRepo.save(event);
        return eventMapper.toEventResponse(event);
    }

    //Tạo mã cho sự kiện tiếp theo
    private String generateNextEventID() {
        Optional<Event> lastEvent = eventRepo.findTopByOrderByEventIdDesc();
        String currentYear = String.valueOf(java.time.Year.now().getValue());

        if (lastEvent.isPresent()) {
            String lastEventID = lastEvent.get().getEventId();
            int numericPart = Integer.parseInt(lastEventID.substring(6)); // Adjusted to skip the year and "SK"
            int nextNumericPart = numericPart + 1;

            return currentYear + "SK" + String.format("%03d", nextNumericPart);
        } else {
            return currentYear + "SK001";
        }
    }


    //Admin cập nhật sự kiện
    public EventResponse updateEvent(String eventID,EventUpdateRequest request) {
        var event = eventRepo.findByEventId(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        eventMapper.updateEvent(event, request);
        event = eventRepo.save(event);
        return eventMapper.toEventResponse(event);
    }

    //Xem chi tiết sự kiện
    public EventResponse getEventInfoById(String eventID) {
        Event event = eventRepo.findByEventId(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        return eventMapper.toEventResponse(event);
    }

    //Xem danh sách người tham gia sự kiện
    public ParticipantsResponse getAll_UserAttendEvent(String eventID) {
        Event event = eventRepo.findByEventId(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        return eventMapper.toParticipantsResponse(event);
    }


    //Admin xóa sự kiện
    public EventResponse deleteEvent(String eventID) {
        Event event = eventRepo.findByEventId(eventID)
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
        var event = eventRepo.findByEventId(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));
        return EventNameResponse.builder()
                .name(event.getName())
                .build();
    }

    //Thêm sinh viên vào document sự kiện
    public EventParticipantResponse addParticipant(String eventID) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Event event = eventRepo.findByEventId(eventID)
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

    //Xóa sinh viên khỏi document sự kiện
    public EventParticipantResponse deleteParticipant(String eventID) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Event event = eventRepo.findByEventId(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));

        // Check if the user exists
        userRepo.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Check if the user is already registered for the event
        boolean alreadyRegistered = event.getParticipants().stream()
                .anyMatch(participant -> participant.getUserName().equals(name));
        if (!alreadyRegistered) {
            throw new AppException(ErrorCode.NOT_REGISTERED);
        }

        // Remove the participant from the event
        event.getParticipants().removeIf(participant -> participant.getUserName().equals(name));
        event = eventRepo.save(event);

        return eventMapper.toEventParticipantResponse(event);
    }

    public EventParticipantResponse checkInEvent(String eventID, String userName) {
        Event event = eventRepo.findByEventId(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));

        // Check if the user exists
        userRepo.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Check if the user has already checked in
        boolean alreadyCheckedIn = event.getParticipants().stream()
                .anyMatch(participant -> participant.getUserName().equals(userName) && participant.isCheckInStatus());
        if (alreadyCheckedIn) {
            throw new AppException(ErrorCode.ALREADY_CHECKED_IN);
        }

        // Update the check-in status and set the check-in time
        event.getParticipants().stream()
                .filter(participant -> participant.getUserName().equals(userName))
                .forEach(participant -> {
                    participant.setCheckInStatus(true);
                    participant.setCheckInTime(new Date()); // Set the check-in time to now
                });

        event = eventRepo.save(event);

        return eventMapper.toEventParticipantResponse(event);
    }

    // Thay đổi trạng thái check-out của sinh viên trong document Event
    public EventParticipantResponse checkOutEvent(String eventID, String userName) {
        Event event = eventRepo.findByEventId(eventID)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));

        // Check if the user exists
        userRepo.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Check if the user has already checked out
        boolean alreadyCheckedOut = event.getParticipants().stream()
                .anyMatch(participant -> participant.getUserName().equals(userName) && participant.isCheckOutStatus());
        if (alreadyCheckedOut) {
            throw new AppException(ErrorCode.ALREADY_CHECKED_IN);
        }

        // Update the check-out status and set the check-out time
        event.getParticipants().stream()
                .filter(participant -> participant.getUserName().equals(userName))
                .forEach(participant -> {
                    participant.setCheckOutStatus(true);
                    participant.setCheckOutTime(new Date()); // Set the check-out time to now
                });

        event = eventRepo.save(event);

        return eventMapper.toEventParticipantResponse(event);
    }
}
