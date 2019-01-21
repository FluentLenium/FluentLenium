package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

public class IntegrationAdvancedUserInteractionFluentTest extends FluentTest {

    protected static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");

    @Override
    public WebDriver newWebDriver() {
        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(BrowserVersion.CHROME);
        htmlUnitDriver.setJavascriptEnabled(true);
        return htmlUnitDriver;
    }
}
