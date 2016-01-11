package org.fluentlenium.it;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class FluentIntegTest extends FluentTest {
    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }
}
