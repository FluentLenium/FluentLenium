package org.fluentlenium.adapter.testng.integration.localtest;

import org.fluentlenium.adapter.testng.FluentTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fluentlenium.adapter.testng.integration.util.UrlUtil.getAbsoluteUrlFromFile;

public abstract class LocalFluentCase extends FluentTestNg {

    public static final String DEFAULT_URL;
    public static final String PAGE_2_URL;

    static {
        DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
        PAGE_2_URL = getAbsoluteUrlFromFile("page2.html");
    }

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }
}
