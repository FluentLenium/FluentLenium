package org.fluentlenium.adapter.cucumber.integration.configuration.hook.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SimpleScenarioMultiStep2 extends BaseTest {


    @When(value = "scenario multi2 I click on next page")
    public void step2() {
        page.$("a#linkToPage2").click();
    }

    @Then(value = "scenario multi2 I am on the second page")
    public void step3() {
        page2.isAt();
    }

}
