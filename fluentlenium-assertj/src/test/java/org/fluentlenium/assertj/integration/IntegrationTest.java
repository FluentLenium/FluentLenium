package org.fluentlenium.assertj.integration;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.fluentlenium.adapter.testng.FluentTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;

public abstract class IntegrationTest extends FluentTestNg {

    protected static final String DEFAULT_URL = getAbsoluteUrlFromFile("index.html");

    @Override
    public WebDriver newWebDriver() {
        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(BrowserVersion.CHROME);
        htmlUnitDriver.setJavascriptEnabled(true);
        return htmlUnitDriver;
    }

}
