package org.fluentlenium.integration.localtest;

import static org.fluentlenium.integration.util.UrlUtil.getAbsoluteUrlFromFile;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

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
