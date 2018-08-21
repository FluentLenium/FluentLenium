package org.fluentlenium.adapter.cucumber.integration.configuration.factory;

import org.fluentlenium.configuration.ConfigurationDefaults;

public class TestConfiguration extends ConfigurationDefaults {

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }
}
