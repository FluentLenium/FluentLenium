package org.fluentlenium.adapter.cucumber.integration.tests.setbean.steps;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;

public class BaseTest extends FluentCucumberTest {

    public BaseTest() {
        setWebDriver("htmlunit");
    }
}
