package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.multiinheritance.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.fluentlenium.adapter.cucumber.FluentCucumberTest;

public class Hooks extends FluentCucumberTest {

    @Before
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }

    @After
    public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}
