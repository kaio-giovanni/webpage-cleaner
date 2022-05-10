package com.project.study.websearch.service;

import com.project.study.websearch.dto.SerpResponseDto;
import com.project.study.websearch.log.Log;
import com.project.study.websearch.utils.FileUtils;

import java.io.File;
import java.io.IOException;

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

    public byte[] getCleanPagePdf(String keyWords) throws IOException {
        String htmlCleaned = getCleanPage(keyWords);
        logger.info("Converting to file");
        File htmlFile = FileUtils.writeHtml(htmlCleaned);
        assert htmlFile != null;
        return externalApiService.callPdfConverterApi(htmlFile);
    }

    public String getCleanPage(String keyWords) {
        logger.info("Getting results from web..");
        SerpResponseDto dto = serpApiService.mockedData(keyWords);
        logger.info("Choosing the best result");
        String url = dto.getOrganicResults()
                .stream()
                .findFirst()
                .get()
                .getLink();
        logger.info("Getting page source code");
        String htmlCode = driverService.getPageSource(url);
        logger.info("Cleaning the page...");
        return new JsoupService(htmlCode, url).cleanPage();
    }
}
