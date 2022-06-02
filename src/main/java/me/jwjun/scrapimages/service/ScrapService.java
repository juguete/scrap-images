package me.jwjun.scrapimages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjun.scrapimages.ImageDownloadEvent;
import me.jwjun.scrapimages.rest.dto.CreateScrapReq;
import me.jwjun.scrapimages.service.entity.ImageInfo;
import me.jwjun.scrapimages.service.entity.Scrap;
import me.jwjun.scrapimages.service.repository.ScrapRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScrapService {

    final ScrapRepository scrapRepository;

    final ApplicationEventPublisher eventPublisher;

    public Scrap createScrap(CreateScrapReq req) {
        ImageInfo imageInfo = ImageInfo.builder()
                .url(req.getUrl())
                .isDownloaded(false)
                .build();

        Scrap scrap = Scrap.builder()
                .userId(req.getUserId())
                .title(req.getTitle())
                .description(req.getDesc())
                .imageInfo(imageInfo)
                .build();

        Scrap saved = scrapRepository.save(scrap);
        eventPublisher.publishEvent(new ImageDownloadEvent(this, saved.getImageInfo().getImageId()));
        return saved;
    }
}
