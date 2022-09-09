package com.project.study.websearch.controller;

import com.project.study.websearch.service.WebPageCleanerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/clean")
public class WebPageCleanerController {

    private final WebPageCleanerService service = new WebPageCleanerService();

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getCleanPage(@RequestParam(name = "url") String url) throws IOException, URISyntaxException {
        byte[] content = service.getCleanPagePdfByUrl(url);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @GetMapping(value = "/page/search", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPageFileInCleanMode(@RequestParam(name = "q") String query)
            throws IOException, URISyntaxException {
        byte[] content = service.getCleanPagePdfByKeyWords(query);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

}
