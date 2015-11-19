package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public abstract class LocalFluentCase extends FluentTest {
    public static final String BASE_URL = getPath();
    public static final String DEFAULT_URL = BASE_URL+"index.html";
    public static final String JAVASCRIPT_URL = BASE_URL+"javascript.html";

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }
    public static String getPath() {
        String currentDir = System.getProperty("user.dir");
        if (!currentDir.endsWith("/fluentlenium-core")){
            currentDir+="/fluentlenium-core";
        }
        String scheme = "file:/";
        if (currentDir.startsWith("/home")) {
            scheme = "file:";
        }
        return scheme + currentDir + "/src/test/html/";
    }
}

