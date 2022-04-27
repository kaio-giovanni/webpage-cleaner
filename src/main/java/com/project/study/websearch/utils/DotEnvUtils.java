package com.project.study.websearch.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnvUtils {

    private DotEnvUtils() {
    }

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String getSerpiApiKey() {
        return dotenv.get("SERPI_API_KEY");
    }
}