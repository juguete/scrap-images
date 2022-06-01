package me.jwjun.scrapimages.service.repository;

import me.jwjun.scrapimages.service.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {
}
