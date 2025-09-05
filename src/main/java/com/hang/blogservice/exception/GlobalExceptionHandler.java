package com.hang.blogservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // default handler for validation annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        System.out.println(ex.toString());
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    System.out.println(error.toString());
                    errors.put(error.getField(), error.getDefaultMessage());
                });


        return ResponseEntity.badRequest().body(errors);

    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Map<String, String>> EmailExistsExceptionHandler(EmailExistsException ex) {
        log.warn("Email already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("Email", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        log.warn("Resource not found already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("resource:", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> AuthenticationExceptionHandler(AuthenticationException ex) {
        log.warn("Authentication already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> InvalidTokenExceptionHandler(InvalidTokenException ex) {
        log.warn("Invalid token {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        return ResponseEntity.badRequest().body(errors);
    }
}
