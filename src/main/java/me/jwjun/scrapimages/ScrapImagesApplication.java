package me.jwjun.scrapimages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class ScrapImagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrapImagesApplication.class, args);
	}

}

