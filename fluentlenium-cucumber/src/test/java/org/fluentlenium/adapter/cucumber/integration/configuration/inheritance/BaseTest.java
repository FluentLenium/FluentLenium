package org.fluentlenium.adapter.cucumber.integration.configuration.inheritance;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;

public class BaseTest extends FluentCucumberTest {

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }
}
