package org.fluentlenium.adapter.cucumber.integration.driverperscenario.steps;

import cucumber.api.java.en.Given;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;

public class SimpleScenarioMultiStep1 extends BaseTest {

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        page = newInstance(LocalPage.class);
        goTo(page);
    }
}
