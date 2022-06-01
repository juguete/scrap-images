package me.jwjun.scrapimages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjun.scrapimages.ImageDownloadEvent;
import me.jwjun.scrapimages.service.entity.ImageInfo;
import me.jwjun.scrapimages.service.repository.ImageInfoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    final ImageInfoRepository imageInfoRepository;

    @Async
    @EventListener(classes = ImageDownloadEvent.class)
    public void eventReceived(ImageDownloadEvent event) {
        log.info("imageId: {}", event.getImageId());

        Optional<ImageInfo> optImageInfo = imageInfoRepository.findById(event.getImageId());

        optImageInfo.ifPresent( imageInfo -> {
            log.info("imageInfo: {}", imageInfo);

            downloadImage(imageInfo.getUrl());
        });
    }

    private void downloadImage(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedImage image = ImageIO.read(url);

            log.info("url.getFile(): {}", url.getFile());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
