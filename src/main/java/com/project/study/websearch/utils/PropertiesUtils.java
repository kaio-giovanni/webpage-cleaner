package com.project.study.websearch.utils;

import org.openqa.selenium.phantomjs.PhantomJSDriverService;

public class PropertiesUtils {

    private PropertiesUtils () {
        // Do nothing
    }

    public static void loadByDotEnv () {
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./src/main/resources/static/phantomjs.exe");
    }
}