package com.example.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<String> handleException(Exception e) {
         return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
         }
}
