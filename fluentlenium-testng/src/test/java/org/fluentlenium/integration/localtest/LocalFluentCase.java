package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.FluentTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public abstract class LocalFluentCase extends FluentTestNg {
    private static final String BASE_URL = getPath();
    public static final String DEFAULT_URL = BASE_URL + "index.html";

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }

    public static String getPath() {
        String currentDir = System.getProperty("user.dir");
        if (!currentDir.endsWith("/fluentlenium-testng")) {
            currentDir += "/fluentlenium-testng";
        }
        String scheme = "file:/";
        if (currentDir.startsWith("/home")) {
            scheme = "file:";
        }
        return scheme + currentDir + "/src/test/html/";
    }
}

