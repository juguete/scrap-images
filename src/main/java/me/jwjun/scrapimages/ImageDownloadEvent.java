package me.jwjun.scrapimages;

import org.springframework.context.ApplicationEvent;

public class ImageDownloadEvent extends ApplicationEvent {

    private final Long imageId;

    public ImageDownloadEvent(Object source, Long imageId) {
        super(source);
        this.imageId = imageId;
    }

    public Long getImageId() {
        return imageId;
    }
}
