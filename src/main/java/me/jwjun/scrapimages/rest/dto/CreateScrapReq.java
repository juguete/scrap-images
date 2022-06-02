package me.jwjun.scrapimages.rest.dto;

import lombok.Data;
import me.jwjun.scrapimages.service.entity.Scrap;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URL;

@Data
public class CreateScrapReq {

    private Long userId;

    private String url;

    private String title;

    private String desc;

}
