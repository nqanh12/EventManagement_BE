package com.example.EventManagement_SpringBoot.dto.request;

import com.example.EventManagement_SpringBoot.entity.Users;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {


    @Size(min=8,message = "MSSV phải có 8 kí tự")
         String userName;

    @Size(min = 8 ,max = 20, message = "Mật khẩu phải dài hơn 8 kí tự")
     String password;

     Set<String> roles;

     List<Users.EventRegistration> eventsRegistered = new ArrayList<>();

}
