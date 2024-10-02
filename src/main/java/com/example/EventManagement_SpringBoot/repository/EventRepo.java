package com.example.EventManagement_SpringBoot.repository;

import com.example.EventManagement_SpringBoot.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepo extends MongoRepository<Event,String> {
    Optional<Event> findTopByOrderByEventIDDesc();
}
