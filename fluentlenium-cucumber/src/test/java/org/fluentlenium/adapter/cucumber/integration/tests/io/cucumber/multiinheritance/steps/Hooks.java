package org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.multiinheritance.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;

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
