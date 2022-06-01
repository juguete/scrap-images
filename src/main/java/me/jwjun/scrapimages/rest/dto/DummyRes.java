package me.jwjun.scrapimages.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class DummyRes {
    int code;
    String msg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime serverTime;
}
