package com.micro.ecommerce.exceptions;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationServiceExceptionHandler {

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<String> handleMessagingException(final MessagingException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unable to send an email: '%s'".formatted(e.getMessage()));
    }
}
