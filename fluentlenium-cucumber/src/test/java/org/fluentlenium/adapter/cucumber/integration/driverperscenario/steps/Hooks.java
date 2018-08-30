package org.fluentlenium.adapter.cucumber.integration.driverperscenario.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.fluentlenium.adapter.cucumber.integration.driverperscenario.BaseTest;

public class Hooks extends BaseTest {

    @Before
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }

    @After
    public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}
