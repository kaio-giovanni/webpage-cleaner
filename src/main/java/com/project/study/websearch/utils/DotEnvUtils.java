package com.project.study.websearch.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnvUtils {

    private DotEnvUtils() {
    }

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String getSerpApiKey() {
        return dotenv.get("SERP_API_KEY");
    }
}