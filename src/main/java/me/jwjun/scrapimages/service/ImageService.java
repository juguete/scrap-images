package me.jwjun.scrapimages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjun.scrapimages.ImageDownloadEvent;
import me.jwjun.scrapimages.service.entity.ImageInfo;
import me.jwjun.scrapimages.service.repository.ImageInfoRepository;
import net.sf.jmimemagic.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    final ImageInfoRepository imageInfoRepository;

    @Value("${app.settings.download-path}")
    String downloadFolder;

    @Async
    @EventListener(classes = ImageDownloadEvent.class)
    public void eventReceived(ImageDownloadEvent event) throws MalformedURLException {
        log.info("imageId: {}", event.getImageId());

        ImageInfo imageInfo = imageInfoRepository.findById(event.getImageId())
                .orElseThrow(() -> new RuntimeException(event.getImageId() + " 이미지가 없습니다."));

        final File downloadPath = new File(downloadFolder, "" + event.getImageId());

        if (!downloadPath.exists()) {
            URL url = new URL(imageInfo.getUrl());
            try (InputStream is = url.openStream()) {
                Files.copy(is, downloadPath.toPath());
            } catch (Exception e) {
                log.error("", e);
            }
        }


        imageInfo.setLocalPath(downloadPath.getAbsolutePath());
        imageInfo.setIsDownloaded(true);

        BufferedImage image = null;
        try {
            Magic magic = new Magic();
            MagicMatch match = magic.getMagicMatch(downloadPath, false);
            String mimeType = match.getMimeType();
            log.info("mimeType:{}", mimeType);
            imageInfo.setMimeType(mimeType);

            image = ImageIO.read(downloadPath);

            int width = image.getWidth();
            int height = image.getHeight();
            imageInfo.setHeight(height);
            imageInfo.setWidth(width);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageInfoRepository.save(imageInfo);
    }
}
