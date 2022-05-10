package com.project.study.websearch.service;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ExternalApiService {

    private static final MediaType TEXT_HTML = MediaType.parse("text/html; charset=utf-8");
    private static final long TIMEOUT = 15;
    private final OkHttpClient httpClient;

    public ExternalApiService() {
        this.httpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public Response makeGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();

        return httpClient.newCall(request).execute();
    }

    public Response makePostRequest(String url, RequestBody requestBody) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .build();

        return httpClient.newCall(request).execute();
    }

    public byte[] callPdfConverterApi(File htmlFile) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", htmlFile.getName(),
                        RequestBody.create(htmlFile, TEXT_HTML))
                .build();

        String apiUrl = "https://topdfconverterapi.herokuapp.com/api/v1/html2pdf";
        Response response = makePostRequest(apiUrl, requestBody);
        return Objects.requireNonNull(response.body()).bytes();
    }

}
