package com.example.EventManagement_SpringBoot.exception;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1001, "User not existed", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1002, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "User not found",  HttpStatus.NOT_FOUND),
    AUTHENTICATION_FAILED(1004, "Authentication failed", HttpStatus.UNAUTHORIZED),
    TOKEN_GENERATION_FAILED(1005, "Token generation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CREDENTIALS(1006, "Invalid credentials", HttpStatus.BAD_REQUEST),
    EVENT_EXISTED(1007, "Event already existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1008, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),
    EVENT_NOT_FOUND(1009, "Event not found", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(1010, "Invalid password", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_SAME_AS_OLD(1011, "New password is the same as the old password", HttpStatus.BAD_REQUEST),
    ALREADY_REGISTERED(1012, "Already registered event", HttpStatus.BAD_REQUEST),
    NOT_REGISTERED(1013, "Not registered event", HttpStatus.BAD_REQUEST),
    ALREADY_CHECKED_IN(1014, "Already checked in", HttpStatus.BAD_REQUEST),;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
     int code;
     String message;
     HttpStatusCode httpStatusCode;

}
