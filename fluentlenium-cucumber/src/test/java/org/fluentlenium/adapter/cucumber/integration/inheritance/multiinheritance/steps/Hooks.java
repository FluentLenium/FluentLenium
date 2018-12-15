package org.fluentlenium.adapter.cucumber.integration.inheritance.multiinheritance.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;

public class Hooks extends FluentCucumberTest {

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }

    @After
    public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}
