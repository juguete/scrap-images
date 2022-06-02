package me.jwjun.scrapimages.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jwjun.scrapimages.rest.dto.CreateScrapReq;
import me.jwjun.scrapimages.rest.dto.CreateScrapRes;
import me.jwjun.scrapimages.rest.dto.ModifyScrapReq;
import me.jwjun.scrapimages.rest.dto.ModifyScrapRes;
import me.jwjun.scrapimages.service.ScrapService;
import me.jwjun.scrapimages.service.entity.ImageInfo;
import me.jwjun.scrapimages.service.entity.Scrap;
import me.jwjun.scrapimages.service.repository.ScrapRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ScrapController {

    final ScrapRepository scrapRepository;

    final ScrapService scrapService;

    @PostMapping("/scrap/create")
    public ResponseEntity<CreateScrapRes> createScrap(@RequestBody CreateScrapReq req) {
        log.info("req:{}", req);

        Scrap saved = scrapService.createScrap(req);

        CreateScrapRes res = CreateScrapRes.builder().scrapId(saved.getScrapId()).build();
        log.info("req:{}, res:{}", req, res);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/scrap/modify/{scrapId}")
    public ResponseEntity<ModifyScrapRes> modifyScrap(@PathVariable Long scrapId, @RequestBody ModifyScrapReq req) {
        log.info("scrapId:{}, req:{}", scrapId, req);
        Optional<Scrap> optScrap = scrapRepository.findByUserIdAndScrapId(req.getUserId(), scrapId);

        Long resultScrapId = optScrap.map(Scrap::getScrapId).orElse(-1L);

        return ResponseEntity.ok(ModifyScrapRes.builder().scrapId(resultScrapId).build());
    }

    @DeleteMapping("/scrap/remove/{scrapId}")
    public void removeScrap(@PathVariable Long scrapId){
        Optional<Scrap> targetScrap = scrapRepository.findById(scrapId);
    }

    @GetMapping("/scrap/list")
    public void listScrap(){

    }

    @GetMapping("/scrap/{scrapId}")
    public void getScrap(@PathVariable Long scrapId) {

    }
}