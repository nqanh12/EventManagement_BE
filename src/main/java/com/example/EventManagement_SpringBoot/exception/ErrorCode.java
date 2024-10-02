package com.example.EventManagement_SpringBoot.exception;


public enum ErrorCode {
    USER_EXISTED(1001, "User existed"),
    USER_NOT_EXISTED(1001, "User not existed"),
    INVALID_KEY(1002, "Invalid key"),
    USER_NOT_FOUND(1003, "User not found"),
    AUTHENTICATION_FAILED(1004, "Authentication failed"),
    TOKEN_GENERATION_FAILED(1005, "Token generation failed"),
    INVALID_CREDENTIALS(1006, "Invalid credentials");
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
