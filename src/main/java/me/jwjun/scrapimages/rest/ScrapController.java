package me.jwjun.scrapimages.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjun.scrapimages.rest.dto.*;
import me.jwjun.scrapimages.service.ScrapService;
import me.jwjun.scrapimages.service.entity.ImageInfo;
import me.jwjun.scrapimages.service.entity.Scrap;
import me.jwjun.scrapimages.service.repository.ScrapRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ScrapController {

    final ScrapRepository scrapRepository;

    final ScrapService scrapService;

    @PostMapping("/scrap/create")
    public CreateScrapRes createScrap(@RequestBody CreateScrapReq req) {
        log.info("req:{}", req);

        try {
            new URL(req.getUrl()); // check valid url
            Scrap saved = scrapService.createScrap(req);
            CreateScrapRes res = CreateScrapRes.builder().scrapId(saved.getScrapId()).build();
            log.info("req:{}, res:{}", req, res);
            return res;
        } catch (MalformedURLException e) {
            log.warn("잘못된 이미지 url: {}", req.getUrl(), e);
            throw new RestException("잘못된 이미지 url: " + req.getUrl() );
        }
    }

    @PutMapping("/scrap/modify/{scrapId}")
    public ModifyScrapRes modifyScrap(@PathVariable Long scrapId, @RequestBody ModifyScrapReq req) {
        log.info("scrapId:{}, req:{}", scrapId, req);
        Scrap scrap = scrapService.modifyScrap(scrapId, req);

        return ModifyScrapRes.builder()
                .scrapId(scrap.getScrapId())
                .title(scrap.getTitle())
                .desc(scrap.getDescription())
                .build();
    }

    @DeleteMapping("/scrap/remove/{scrapId}")
    public void removeScrap(@PathVariable Long scrapId){
        scrapService.removeScrap(scrapId);
    }

    @GetMapping("/scrap/{scrapId}")
    public GetScrapRes getScrap(@PathVariable Long scrapId) {
        Scrap scrapInfo = scrapService.getScrap(scrapId);
        return GetScrapRes.builder()
                .scrapId(scrapInfo.getScrapId())
                .userId(scrapInfo.getUserId())
                .title(scrapInfo.getTitle())
                .description(scrapInfo.getDescription())
                .viewCnt(scrapInfo.getViewCnt())
                .build();
    }

    @GetMapping("/img/{scrapId}")
    /**
     * 스크랩아이디로 로컬 이미지를 보여준다.
     */
    public ResponseEntity<byte[]> getScrapImage(@PathVariable Long scrapId) throws IOException {
        Scrap scrapInfo = scrapService.getScrap(scrapId);
        ImageInfo imageInfo = scrapInfo.getImageInfo();
        Boolean isDownloaded = imageInfo.getIsDownloaded();
        if( !isDownloaded ) {
            throw new RestException("이미지를 불러올 수 없습니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        byte[] media = IOUtils.toByteArray(new FileInputStream(new File(imageInfo.getLocalPath())));
        headers.set("content-type", imageInfo.getMimeType());
        return ResponseEntity.ok().header("content-type", imageInfo.getMimeType()).body(media);
    }

    @GetMapping("/scrap/list")
    public void listScrap() {
        throw new UnsupportedOperationException("페이징 처리");
    }
}