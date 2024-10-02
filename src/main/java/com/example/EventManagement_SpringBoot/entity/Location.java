package com.example.EventManagement_SpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Location")
@JsonPropertyOrder({
        "id", "locationName", "locationAddress", "capacity"
})
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @Id
     String id;
     String locationName;
     String locationAddress;
     int capacity;

}
