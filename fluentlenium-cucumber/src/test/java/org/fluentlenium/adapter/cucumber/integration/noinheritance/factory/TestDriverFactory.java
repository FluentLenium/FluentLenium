package org.fluentlenium.adapter.cucumber.integration.noinheritance.factory;

import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FactoryName;
import org.fluentlenium.configuration.WebDriverFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@FactoryName("test")
public class TestDriverFactory implements WebDriverFactory {

    @Override
    public WebDriver newWebDriver(Capabilities desiredCapabilities, ConfigurationProperties configuration) {
        return new HtmlUnitDriver();
    }
}
