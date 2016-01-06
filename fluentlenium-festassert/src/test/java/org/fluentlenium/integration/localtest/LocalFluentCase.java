package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;

//TODO : Problem here - 1 instance by test when 1 instance for test suite is sufficient ...
public abstract class LocalFluentCase extends FluentTest {

    public static final String DEFAULT_URL;

    static {
        DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
    }

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }
}
