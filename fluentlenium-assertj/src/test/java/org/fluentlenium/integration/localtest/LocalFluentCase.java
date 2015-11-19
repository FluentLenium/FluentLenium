package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

//TODO : Problem here - 1 instance by test when 1 instance for test suite is sufficient ...
public abstract class LocalFluentCase extends FluentTest {
    private static final String BASE_URL = getPath();
    protected static final String DEFAULT_URL = BASE_URL+"index.html";

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }

    public static String getPath() {
        String currentDir = System.getProperty("user.dir");
        if (!currentDir.endsWith("/fluentlenium-assertj")){
            currentDir+="/fluentlenium-assertj";
        }
        String scheme = "file:/";
        if (currentDir.startsWith("/home")) {
            scheme = "file:";
        }
        return scheme + currentDir + "/src/test/html/";
    }
}

