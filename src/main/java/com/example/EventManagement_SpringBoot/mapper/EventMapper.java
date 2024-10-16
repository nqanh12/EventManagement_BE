package com.example.EventManagement_SpringBoot.mapper;

import com.example.EventManagement_SpringBoot.dto.request.EventCreationRequest;
import com.example.EventManagement_SpringBoot.dto.request.EventUpdateRequest;
import com.example.EventManagement_SpringBoot.dto.response.EventListResponse;
import com.example.EventManagement_SpringBoot.dto.response.EventParticipantResponse;
import com.example.EventManagement_SpringBoot.dto.response.EventResponse;
import com.example.EventManagement_SpringBoot.dto.response.ParticipantsResponse;
import com.example.EventManagement_SpringBoot.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    List<EventListResponse> toListEventResponse(List<Event> events);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "locationId", source = "locationId")
    @Mapping(target = "dateStart", source = "dateStart")
    @Mapping(target = "dateEnd", source = "dateEnd")
    @Mapping(target = "managerName", source = "managerName")
    @Mapping(target = "participants", source = "participants")
    EventResponse toEventResponse(Event event);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "dateEnd", source = "dateEnd")
    @Mapping(target = "dateStart", source = "dateStart")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "locationId", source = "locationId")
    @Mapping(target = "managerName", source = "managerName")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "participants", ignore = true)
    Event toEvent(EventCreationRequest request);


    ParticipantsResponse toParticipantsResponse(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventId", ignore = true)
    @Mapping(target = "dateEnd", source = "dateEnd")
    @Mapping(target = "dateStart", source = "dateStart")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "locationId", source = "locationId")
    @Mapping(target = "managerName", source = "managerName")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "participants", ignore = true)
    void updateEvent(@MappingTarget Event event, EventUpdateRequest request);

    EventParticipantResponse toEventParticipantResponse(Event event);
}
