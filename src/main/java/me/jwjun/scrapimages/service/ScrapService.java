package me.jwjun.scrapimages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjun.scrapimages.ImageDownloadEvent;
import me.jwjun.scrapimages.rest.RestException;
import me.jwjun.scrapimages.rest.dto.CreateScrapReq;
import me.jwjun.scrapimages.rest.dto.ModifyScrapReq;
import me.jwjun.scrapimages.service.entity.ImageInfo;
import me.jwjun.scrapimages.service.entity.Scrap;
import me.jwjun.scrapimages.service.repository.ScrapRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScrapService {

    final ScrapRepository scrapRepository;

    final ApplicationEventPublisher eventPublisher;

    public Scrap createScrap(CreateScrapReq req) {
        ImageInfo imageInfo = ImageInfo.builder()
                .url(req.getUrl()) //validate url
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

    public Scrap modifyScrap(Long scrapId, ModifyScrapReq req) {
        Optional<Scrap> optScrap = scrapRepository.findByUserIdAndScrapId(req.getUserId(), scrapId);
        Scrap scrap = optScrap.orElseThrow(() -> new RestException("해당하는 스크랩이 없습니다. scrapId=" + scrapId));
        scrap.setDescription(req.getDesc());
        scrap.setTitle(req.getTitle());
//        scrap.setUpdateAt(LocalDateTime.now());

        Scrap saved = scrapRepository.save(scrap);
        return saved;
    }

    public void removeScrap(Long scrapId) {
        Optional<Scrap> optScrap = scrapRepository.findById(scrapId);
        Scrap scrap = optScrap.orElseThrow(() -> new RestException("해당하는 스크랩이 없습니다. scrapId=" + scrapId));
        scrapRepository.deleteById(scrap.getScrapId());
    }

    public Scrap getScrap(Long scrapId) {
        Optional<Scrap> optScrap = scrapRepository.findById(scrapId);
        Scrap scrap = optScrap.orElseThrow(() -> new RestException("해당하는 스크랩이 없습니다. scrapId=" + scrapId));
        scrap.setViewCnt(scrap.getViewCnt() + 1L);
        Scrap saved = scrapRepository.save(scrap);
        return saved;
    }
}
