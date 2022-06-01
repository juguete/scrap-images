package me.jwjun.scrapimages.rest.dto;

import lombok.Data;
import me.jwjun.scrapimages.service.entity.Scrap;

import java.net.URL;

@Data
public class CreateScrapReq {

    private String url;

    private String title;

    private String desc;

}
