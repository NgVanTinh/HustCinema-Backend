package com.hustcinema.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlingRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<String> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getFieldError().getDefaultMessage());
    }
    

    // @ExceptionHandler(value = Exception.class)
    
    // ResponseEntity<ApiRespond> handlingRuntimeException(RuntimeException exception) {
    //     System.out.println("Exception: " + exception);
    //     ApiRespond apiRespond = new ApiRespond();

    //     apiRespond.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    //     apiRespond.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

    //     return ResponseEntity.badRequest().body(apiRespond);
    // }

    // @ExceptionHandler(value = AppException.class)
    // ResponseEntity<ApiRespond> handlingAppException(AppException exception) {
    //     ErrorCode errorCode = exception.getErrorCode();
    //     ApiRespond apiRespond = new ApiRespond();

    //     apiRespond.setCode(errorCode.getCode());
    //     apiRespond.setMessage(errorCode.getMessage());

    //     return ResponseEntity.status(errorCode.getStatusCode()).body(apiRespond);
    // }

    // @ExceptionHandler(value = AccessDeniedException.class)
    // ResponseEntity<ApiRespond> handlingAccessDeniedException(AccessDeniedException exception) {
    //     ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    //     ApiRespond apiRespond = new ApiRespond();
    //     apiRespond.setCode(errorCode.getCode());
    //     apiRespond.setMessage(errorCode.getMessage());
    //     return ResponseEntity.status(errorCode.getStatusCode()).body(apiRespond);
    // }

}
