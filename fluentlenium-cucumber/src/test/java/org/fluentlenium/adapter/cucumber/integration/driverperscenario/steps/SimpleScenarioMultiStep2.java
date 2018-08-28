package org.fluentlenium.adapter.cucumber.integration.driverperscenario.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;

public class SimpleScenarioMultiStep2 extends BaseTest {

    @When(value = "scenario multi2 I click on next page")
    public void step2() {
        el("a#linkToPage2").click();
    }

    @Then(value = "scenario multi2 I am on the second page")
    public void step3() {
        newInstance(LocalPage2.class).isAt();
    }
}
