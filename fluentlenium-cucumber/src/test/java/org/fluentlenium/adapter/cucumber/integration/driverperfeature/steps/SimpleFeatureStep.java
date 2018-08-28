package org.fluentlenium.adapter.cucumber.integration.driverperfeature.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.integration.driverperfeature.BaseTest;

public class SimpleFeatureStep extends BaseTest {

    @Given(value = "feature I am on the first page")
    public void step1() {
        goTo(page);
    }

    @When(value = "feature I click on next page")
    public void step2() {
        el("a#linkToPage2").click();
    }

    @Then(value = "feature I am on the second page")
    public void step3() {
        page2.isAt();
    }
}
