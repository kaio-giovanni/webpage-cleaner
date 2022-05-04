package com.project.study.websearch.controller;

import java.io.File;
import java.util.Optional;

import com.project.study.websearch.dto.GoogleSearchDto;
import com.project.study.websearch.service.ChromeDriverService;
import com.project.study.websearch.service.JsoupService;
import com.project.study.websearch.service.SerpApiService;
import com.project.study.websearch.utils.ConverterUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value = "/search")
public class WebPageCleanerController {

    @GetMapping(value = "/clean-file", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPageFileInCleanMode(@RequestParam(name = "q") String q) {
        GoogleSearchDto dto = SerpApiService.search(q);
        if (dto == null || dto.getOrganicResults().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Optional<GoogleSearchDto.OrganicResults> firstResult = dto.getOrganicResults().stream().findFirst();
        if (firstResult.isPresent()) {
            String url = firstResult.get().getLink();
            String htmlCode = ChromeDriverService.getInstance().getPageSource(url);
            File file = new JsoupService(htmlCode, url).cleanPageFile();
            byte[] binary = ConverterUtils.convertFileToByteArray(file);
            return new ResponseEntity<>(binary, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/mocked-page", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getCleanPage() {
        GoogleSearchDto dto = SerpApiService.mockedData();
        String url = dto.getOrganicResults()
                .stream()
                .findFirst()
                .get()
                .getLink();
        String htmlCode = ChromeDriverService.getInstance().getPageSource(url);
        String htmlCleaned = new JsoupService(htmlCode, url).cleanPage();
        return new ResponseEntity<>(htmlCleaned, HttpStatus.OK);
    }

}
