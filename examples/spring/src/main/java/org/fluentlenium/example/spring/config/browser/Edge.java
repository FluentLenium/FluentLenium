package org.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeOptions;

public class Edge implements IBrowser {

    @Override
    public Capabilities getBrowserCapabilities() {
        return new EdgeOptions();
    }

    @Override
    public String getDriverExecutableName() {
        return "MicrosoftWebDriver";
    }

    @Override
    public String getDriverSystemPropertyName() {
        return "webdriver.edge.driver";
    }
}
