package com.example.EventManagement_SpringBoot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "role")
public class Role {
    @Id
    String name;
    String description;

    public Role(String name) {
        this.name = name;
    }

    @ManyToMany
    Set<Permission> permissions;
}
