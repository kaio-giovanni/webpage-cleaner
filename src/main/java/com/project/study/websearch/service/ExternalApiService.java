package com.project.study.websearch.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ExternalApiService {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    private ExternalApiService() {
        // Do nothing
    }

    public static Response makeGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();

        return HTTP_CLIENT.newCall(request).execute();
    }

}
