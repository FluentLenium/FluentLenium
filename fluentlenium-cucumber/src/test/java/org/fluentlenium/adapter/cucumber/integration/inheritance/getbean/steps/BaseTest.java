package org.fluentlenium.adapter.cucumber.integration.inheritance.getbean.steps;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;

public class BaseTest extends FluentCucumberTest {

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }
}
