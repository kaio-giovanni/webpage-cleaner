package com.project.study.websearch.controller;

import com.project.study.websearch.dto.GoogleSearchDto;
import com.project.study.websearch.service.JsoupService;
import com.project.study.websearch.service.SerpiApiService;
import com.project.study.websearch.utils.ConverterUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Optional;

@RestController
@RequestMapping(value = "/search")
public class WebPageCleanerController {

    @GetMapping(value = "/clean", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPageCleanMode(@RequestParam(name = "q") String q) {
        GoogleSearchDto dto = SerpiApiService.search(q);
        if (dto == null || dto.getOrganicResults().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Optional<GoogleSearchDto.OrganicResults> firstResult = dto.getOrganicResults().stream().findFirst();
        if (firstResult.isPresent()) {
            String url = firstResult.get().getLink();
            File file = new JsoupService(url).cleanPage();
            byte[] binary = ConverterUtils.convertFileToByteArray(file);
            return new ResponseEntity<>(binary, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
