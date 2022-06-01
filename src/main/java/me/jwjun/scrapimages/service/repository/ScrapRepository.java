package me.jwjun.scrapimages.service.repository;

import me.jwjun.scrapimages.service.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserIdAndScrapId(Long userId, Long scrapId);
}