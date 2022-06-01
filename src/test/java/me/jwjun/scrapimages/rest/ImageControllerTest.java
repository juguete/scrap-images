package me.jwjun.scrapimages.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.jwjun.scrapimages.rest.dto.CreateScrapReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createImage() throws Exception {
        CreateScrapReq req = new CreateScrapReq();
        req.setUrl("https://pixabay.com/ko/photos/%ed%8c%ac%eb%8d%94-%eb%a0%88%eb%93%9c-%ed%8c%ac%eb%8d%94-%ea%b3%b0-%eb%aa%a8%ed%94%bc-7015575/");
        req.setTitle("팬더");
        req.setDesc("레드 팬더");

        String body = objectMapper.writeValueAsString(req);
        log.info("body: {}", body);

        this.mockMvc.perform(
                post("/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(body)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(print());
    }
}