package org.fluentlenium.integration.localtest;

import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;
import org.fluentlenium.integration.util.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class IntegrationAdvancedUserInteractionFluentTest extends FluentTest {

    public static final String DEFAULT_URL =  getAbsoluteUrlFromFile("index.html");

    @Override
    public WebDriver newWebDriver() {
        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(BrowserVersion.CHROME);
        htmlUnitDriver.setJavascriptEnabled(true);
        return htmlUnitDriver;
    }
}
