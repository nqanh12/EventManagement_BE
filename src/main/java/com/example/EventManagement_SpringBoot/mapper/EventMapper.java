package com.example.EventManagement_SpringBoot.mapper;

import com.example.EventManagement_SpringBoot.dto.response.EventResponse;
import com.example.EventManagement_SpringBoot.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventResponse toEventResponse(Event event);
}
