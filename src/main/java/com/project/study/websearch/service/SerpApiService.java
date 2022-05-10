package com.project.study.websearch.service;

import com.google.gson.Gson;
import com.project.study.websearch.dto.SerpResponseDto;
import com.project.study.websearch.utils.DotEnvUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SerpApiService {

    private static final String ENGINE = "google";
    private static final int NUM_RESULTS = 10;
    private static final String API_URL_TEMPLATE = "https://serpapi.com/search.json?engine=%s&q=%s&api_key=%s&num=%d";
    private final ExternalApiService externalApiService;

    public SerpApiService(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    public SerpResponseDto search(String keyWords) {
        String keyWordEncoded = URLEncoder.encode(keyWords, StandardCharsets.UTF_8);
        String url = String.format(API_URL_TEMPLATE, ENGINE, keyWordEncoded, DotEnvUtils.getSerpApiKey(), NUM_RESULTS);

        try {
            String json = Objects.requireNonNull(externalApiService
                    .makeGetRequest(url)
                    .body())
                    .string();
            return new Gson().fromJson(json, SerpResponseDto.class);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public SerpResponseDto mockedData(String keyWords) {
        List<SerpResponseDto.OrganicResults> results = new ArrayList<>();
        String link = "https://noticias.uol.com.br/internacional/ultimas-noticias/2022/04/28/russia-ucrania-28-de-abril-dia-64.htm";
        results.add(new SerpResponseDto.OrganicResults()
                .setTitle("title")
                .setLink(link)
                .setPosition(1)
                .setDisplayedLink(link)
                .setSnippet("snippet")
        );

        SerpResponseDto dto = new SerpResponseDto();
        dto.setOrganicResults(results);
        return dto;
    }
}