package org.fluentlenium.adapter.cucumber.integration.noinheritance.factory;

import org.fluentlenium.configuration.ConfigurationDefaults;

public class TestConfiguration extends ConfigurationDefaults {

    @Override
    public String getWebDriver() {
        return "test";
    }
}
