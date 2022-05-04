package com.project.study.websearch.service;

import com.project.study.websearch.log.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverService {
    private static ChromeDriverService service;
    private static final int PAGE_TIMEOUT = 30;
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final Log LOG = new Log(ChromeDriverService.class);
    private final WebDriver webDriver;
    private final WebDriverWait wait;

    private ChromeDriverService() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver(getOptions());
        webDriver.manage().deleteAllCookies();
        webDriver.manage().window().setSize(getDimension());
        wait = new WebDriverWait(webDriver, PAGE_TIMEOUT);
    }

    public static ChromeDriverService getInstance() {
        if (service == null) {
            service = new ChromeDriverService();
        }

        return service;
    }

    private ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("start-maximized");
        options.addArguments("start-fullscreen");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        return options;
    }

    public String getPageSource(String url) {
        LOG.info(String.format("Navigating to url: %s", url));
        webDriver.navigate().to(url);
        LOG.info("Waiting for the page to fully load");
        wait.until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
        LOG.info("Getting page source");
        String htmlCode = webDriver.getPageSource();
        LOG.info("Closing web driver...");
        webDriver.quit();
        return htmlCode;
    }

    private Dimension getDimension() {
        return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

}
