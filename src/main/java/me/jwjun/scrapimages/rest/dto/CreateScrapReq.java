package me.jwjun.scrapimages.rest.dto;

import lombok.Data;

import java.net.MalformedURLException;
import java.net.URL;

@Data
public class CreateScrapReq {

    private Long userId;

    private String url;

    private String title;

    private String desc;

}
