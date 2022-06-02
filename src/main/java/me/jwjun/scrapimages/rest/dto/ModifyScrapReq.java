package me.jwjun.scrapimages.rest.dto;

import lombok.Data;

@Data
public class ModifyScrapReq {

    private Long userId;

    private String title;

    private String desc;
}
