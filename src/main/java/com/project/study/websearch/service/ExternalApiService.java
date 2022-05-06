package com.project.study.websearch.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ExternalApiService {

    private final OkHttpClient httpClient;

    public ExternalApiService() {
        this.httpClient = new OkHttpClient().newBuilder().build();
    }

    public Response makeGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();

        return httpClient.newCall(request).execute();
    }

}
