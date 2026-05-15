package com.library.controller;

import com.library.model.dto.BibleDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/bibles")
@CrossOrigin(origins = "http://localhost:3000")
public class BibleController {

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public BibleDto[] getBiblesFromExternalApi() {
        String url = "https://holy-bible-api.com/bibles";
        return restTemplate.exchange(url, HttpMethod.GET, null, BibleDto[].class).getBody();
    }
}
