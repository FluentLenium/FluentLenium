package org.fluentlenium.integration.localtest;

import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;

import org.fluentlenium.adapter.FluentTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public abstract class LocalFluentCase extends FluentTestNg {

    public static final String DEFAULT_URL;
    public static final String PAGE_2_URL;

    static {
        DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
        PAGE_2_URL = getAbsoluteUrlFromFile("page2.html");
    }

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }
}
