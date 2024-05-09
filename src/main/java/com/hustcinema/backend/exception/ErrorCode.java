package com.hustcinema.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(101, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(102, "User existed", HttpStatus.BAD_REQUEST),
    // USERNAME_INVALID(103, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    // INVALID_PASSWORD(104, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(105, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(106, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(107, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(108, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
    
    
}
