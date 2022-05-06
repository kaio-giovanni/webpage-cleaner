package com.project.study.websearch.controller;

import com.project.study.websearch.dto.SerpResponseDto;
import com.project.study.websearch.service.ChromeDriverService;
import com.project.study.websearch.service.ExternalApiService;
import com.project.study.websearch.service.JsoupService;
import com.project.study.websearch.service.SerpApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/search")
public class WebPageCleanerController {

    private final ChromeDriverService driverService = ChromeDriverService.getInstance();
    private final ExternalApiService externalApiService = new ExternalApiService();
    private final SerpApiService serpApiService = new SerpApiService(externalApiService);

    @GetMapping(value = "/clean-file", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getPageFileInCleanMode(@RequestParam(name = "q") String q) {
        SerpResponseDto dto = serpApiService.mockedData(q);
        if (dto == null || dto.getOrganicResults().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Optional<SerpResponseDto.OrganicResults> firstResult = dto.getOrganicResults().stream().findFirst();
        if (firstResult.isPresent()) {
            String url = firstResult.get().getLink();
            String htmlCode = driverService.getPageSource(url);
            String htmlCleaned = new JsoupService(htmlCode, url).cleanPage();
            return new ResponseEntity<>(htmlCleaned, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/mocked-page", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getCleanPage() {
        SerpResponseDto dto = serpApiService.mockedData("mocked");
        String url = dto.getOrganicResults()
                .stream()
                .findFirst()
                .get()
                .getLink();
        String htmlCode = driverService.getPageSource(url);
        String htmlCleaned = new JsoupService(htmlCode, url).cleanPage();
        return new ResponseEntity<>(htmlCleaned, HttpStatus.OK);
    }

}
