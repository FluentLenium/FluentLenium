package org.fluentlenium.assertj.integration.localtest;

import org.fluentlenium.adapter.junit.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fluentlenium.assertj.integration.util.UrlUtil.getAbsoluteUrlFromFile;

public abstract class LocalFluentCase extends FluentTest {

    public static final String DEFAULT_URL;

    static {
        DEFAULT_URL = getAbsoluteUrlFromFile("index.html");
    }

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }
}
