package me.jwjun.scrapimages.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ScrapControllerErrorHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<Map<String,Object>> onError(RestException e) {
        Map<String,Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", e.getMessage());
        return ResponseEntity.internalServerError().body(body);
    }
}
