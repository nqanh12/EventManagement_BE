package com.example.EventManagement_SpringBoot.repository;

import com.example.EventManagement_SpringBoot.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users,String> {
    Optional<Users> findByUserName(String userName);
}
