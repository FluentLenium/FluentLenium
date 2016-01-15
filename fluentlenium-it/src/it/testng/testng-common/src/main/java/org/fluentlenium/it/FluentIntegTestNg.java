package org.fluentlenium.it;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.adapter.FluentTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class FluentIntegTestNg extends FluentTestNg {
    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }
}
