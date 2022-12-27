package io.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.setbean.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

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
