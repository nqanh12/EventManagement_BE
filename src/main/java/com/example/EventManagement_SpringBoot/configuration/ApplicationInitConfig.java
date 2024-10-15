package com.example.EventManagement_SpringBoot.configuration;

import com.example.EventManagement_SpringBoot.entity.Users;
import com.example.EventManagement_SpringBoot.repository.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.EventManagement_SpringBoot.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepo userRepo) {
        return args -> {
            if (userRepo.findByUserName("hufi2024").isEmpty())
            {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                Users user = Users.builder()
                        .userName("hufi2024")
                        .password(passwordEncoder.encode("123456789"))
                        .roles(roles)
                        .build();

                userRepo.save(user);
            }
        };
    }
}
