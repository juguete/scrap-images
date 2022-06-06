package me.jwjun.scrapimages.rest.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetScrapRes {

    private Long scrapId;

    private Long userId;

    private String title;

    private String description;

    private Long viewCnt;
}
