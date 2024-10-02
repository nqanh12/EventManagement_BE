package com.example.EventManagement_SpringBoot.service;

import com.example.EventManagement_SpringBoot.dto.request.EventCreationRequest;
import com.example.EventManagement_SpringBoot.entity.Event;
import com.example.EventManagement_SpringBoot.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    public Event createEvent(EventCreationRequest request) {
        try{
            String nextEventID = generateNextEventID();
            Event event = new Event();
            event.setEventID(nextEventID);
            event.setName(request.getName());
            event.setDescription(request.getDescription());
            event.setLocationId(request.getLocationId());
            event.setDateStart(request.getDateStart());
            event.setDateEnd(request.getDateEnd());
            event.setManagerId(request.getManagerId());
            return eventRepo.save(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
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
