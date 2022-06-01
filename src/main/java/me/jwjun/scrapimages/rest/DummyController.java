package me.jwjun.scrapimages.rest;

import me.jwjun.scrapimages.rest.dto.DummyRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DummyController {

    @GetMapping("/get")
    public ResponseEntity<DummyRes> get() {
        return ResponseEntity.ok(
                DummyRes.builder()
                        .code(0)
                        .msg("ok")
                        .serverTime(LocalDateTime.now())
                        .build());
    }
}

