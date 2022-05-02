package com.project.study.websearch.service;

import com.project.study.websearch.log.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhantomJsService {

    private final WebDriver webDriver;
    private final WebDriverWait wait;
    private static final int PAGE_TIMEOUT = 30;
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final Log LOG = new Log(PhantomJsService.class);

    public PhantomJsService() {
        LOG.info("Initializing Phantom Web Driver");
        webDriver = new PhantomJSDriver(getCapabilities());
        webDriver.manage().deleteAllCookies();
        webDriver.manage().window().setSize(getDimension());
        wait = new WebDriverWait(webDriver, PAGE_TIMEOUT);
    }

    /**
     * Get screen resolution
     * @return Screen dimension
     */
    private Dimension getDimension() {
        return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Get capabilities of web driver
     * @return The capabilities of Web Driver
     */
    private DesiredCapabilities getCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        String[] phantomJsArgs = {"--web-security=no", "--ignore-ssl-errors=yes"};
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomJsArgs);
        return caps;
    }

    /**
     * Get the page source of website
     * @return Get the source of the loaded page
     */
    public String getPageSource(String url) {
        LOG.info(String.format("Navigating to url: %s", url));
        webDriver.navigate().to(url);
        LOG.info("Waiting for the page to fully load");
        wait.until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
        LOG.info("Getting page source");
        String htmlCode =  webDriver.getPageSource();
        LOG.info("Closing web driver...");
        webDriver.quit();
        return htmlCode;
    }

}
