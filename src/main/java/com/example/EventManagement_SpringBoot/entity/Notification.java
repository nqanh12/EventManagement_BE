package com.example.EventManagement_SpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
@JsonPropertyOrder({
        "id","title", "message", "userName", "eventId", "IsRead"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    String id;
    String title;
    String message;
    String userName;
    String eventId;
    boolean IsRead;
}
