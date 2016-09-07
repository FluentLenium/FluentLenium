package org.fluentlenium.assertj.integration.localtest;

import org.fluentlenium.assertj.integration.util.UrlUtil;
import org.fluentlenium.assertj.integration.util.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public abstract class IntegrationFluentTest extends FluentTest {

    public static final String DEFAULT_URL;

    static {
        DEFAULT_URL = UrlUtil.getAbsoluteUrlFromFile("index.html");
    }

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver(true);
    }
}
