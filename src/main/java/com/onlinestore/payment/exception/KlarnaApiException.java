package com.onlinestore.payment.exception;

import com.onlinestore.payment.model.ErrorV2;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class KlarnaApiException extends RuntimeException {
    
    private final HttpStatus status;
    private final ErrorV2 error;
    
    public KlarnaApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.error = null;
    }
    
    public KlarnaApiException(String message, HttpStatus status, ErrorV2 error) {
        super(message);
        this.status = status;
        this.error = error;
    }
    
    public KlarnaApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
        this.error = null;
    }
}
