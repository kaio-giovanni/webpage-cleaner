package com.project.study.websearch.service;

import com.project.study.websearch.dto.SerpResponseDto;
import com.project.study.websearch.exception.ResourceNotFoundException;
import com.project.study.websearch.log.Log;
import com.project.study.websearch.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class WebPageCleanerService {

    private final ChromeDriverService driverService;
    private final ExternalApiService externalApiService;
    private final SerpApiService serpApiService;
    private final Log logger = new Log(WebPageCleanerService.class);

    public WebPageCleanerService() {
        this.driverService = ChromeDriverService.getInstance();
        this.externalApiService = new ExternalApiService();
        this.serpApiService = new SerpApiService(externalApiService);
    }

    private String getCleanPageByUrl(String url) throws URISyntaxException {
        logger.info("Getting page source code");
        String htmlCode = driverService.getPageSource(url);
        logger.info("Cleaning the page...");
        return new JsoupService(htmlCode, url).cleanPage();
    }

    private String getCleanPageByKeywords(String keyWords) throws URISyntaxException {
        logger.info("Getting results from web..");
        SerpResponseDto dto = serpApiService.search(keyWords);
        logger.info("Choosing the best result");
        Optional<SerpResponseDto.OrganicResults> result = dto.getOrganicResults()
                .stream()
                .findFirst();
        if (result.isPresent()) {
            String url = result
                    .get()
                    .getLink();
            return getCleanPageByUrl(url);
        }

        throw new ResourceNotFoundException("Resource not found for this search");
    }

    private byte[] getPdfByHtmlCode(String htmlCode) throws IOException {
        logger.info("Converting to file");
        File htmlFile = FileUtils.writeHtml(htmlCode);
        assert htmlFile != null;
        return externalApiService.callPdfConverterApi(htmlFile);
    }

    public byte[] getCleanPagePdfByKeyWords(String keyWords) throws IOException, URISyntaxException {
        String htmlCleaned = getCleanPageByKeywords(keyWords);
        return getPdfByHtmlCode(htmlCleaned);
    }

    public byte[] getCleanPagePdfByUrl(String url) throws IOException, URISyntaxException {
        String htmlCleaned = getCleanPageByUrl(url);
        return getPdfByHtmlCode(htmlCleaned);
    }

}
