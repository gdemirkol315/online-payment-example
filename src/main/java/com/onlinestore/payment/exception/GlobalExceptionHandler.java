package com.onlinestore.payment.exception;

import com.onlinestore.payment.model.ErrorV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(KlarnaApiException.class)
    public ResponseEntity<ErrorV2> handleKlarnaApiException(KlarnaApiException ex) {
        if (ex.getError() != null) {
            return new ResponseEntity<>(ex.getError(), ex.getStatus());
        }
        
        ErrorV2 error = ErrorV2.builder()
                .correlationId(UUID.randomUUID().toString())
                .errorCode("KLARNA_API_ERROR")
                .errorMessages(List.of(ex.getMessage()))
                .build();
        
        return new ResponseEntity<>(error, ex.getStatus());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorV2> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ": " + errorMessage);
        });
        
        ErrorV2 error = ErrorV2.builder()
                .correlationId(UUID.randomUUID().toString())
                .errorCode("VALIDATION_ERROR")
                .errorMessages(errors)
                .build();
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorV2> handleGenericException(Exception ex) {
        ErrorV2 error = ErrorV2.builder()
                .correlationId(UUID.randomUUID().toString())
                .errorCode("INTERNAL_SERVER_ERROR")
                .errorMessages(List.of(ex.getMessage()))
                .build();
        
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
