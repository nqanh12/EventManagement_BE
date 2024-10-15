package com.example.EventManagement_SpringBoot.controller;

import com.example.EventManagement_SpringBoot.dto.response.ApiResponse;
import com.example.EventManagement_SpringBoot.dto.request.AuthenticationRequest;
import com.example.EventManagement_SpringBoot.dto.request.IntrospectRequest;
import com.example.EventManagement_SpringBoot.dto.response.AuthenticationResponse;
import com.example.EventManagement_SpringBoot.dto.response.IntrospectResponse;
import com.example.EventManagement_SpringBoot.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationController {
    AuthenticationService authenticationService;


   @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
       var result = authenticationService.authenticate(request);
       return ApiResponse.<AuthenticationResponse>builder()
              .result(result)
               .build();
   }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
