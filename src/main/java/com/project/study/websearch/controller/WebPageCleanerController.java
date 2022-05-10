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

@RestController
@RequestMapping(value = "/search")
public class WebPageCleanerController {

    private final WebPageCleanerService service = new WebPageCleanerService();

    @GetMapping(value = "/clean-page", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getPageFileInCleanMode(@RequestParam(name = "q") String q) {
        String htmlCode = service.getCleanPage(q);
        return new ResponseEntity<>(htmlCode, HttpStatus.OK);
    }

    @GetMapping(value = "/clean-file", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getCleanPage(@RequestParam(name = "q") String q) throws IOException {
        byte[] binary = service.getCleanPagePdf(q);
        return new ResponseEntity<>(binary, HttpStatus.OK);
    }

}
