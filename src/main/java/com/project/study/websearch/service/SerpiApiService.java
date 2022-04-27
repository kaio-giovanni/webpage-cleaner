package com.project.study.websearch.service;

import com.google.gson.Gson;
import com.project.study.websearch.dto.GoogleSearchDto;
import com.project.study.websearch.utils.DotEnvUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SerpiApiService {

    private static final String ENGINE = "google";
    private static final int NUM_RESULTS = 10;
    private static final String API_URL_TEMPLATE = "https://serpapi.com/search.json?engine=%s&q=%s&api_key=%s&num=%d";

    private SerpiApiService() {
        // Do nothing
    }

    public static GoogleSearchDto search(String keyWords) {
        String keyWordEncoded = URLEncoder.encode(keyWords, StandardCharsets.UTF_8);
        String url = String.format(API_URL_TEMPLATE, ENGINE, keyWordEncoded, DotEnvUtils.getSerpiApiKey(), NUM_RESULTS);

        try {
            String json = ExternalApiService.makeGetRequest(url).body().string();
            return new Gson().fromJson(json, GoogleSearchDto.class);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
