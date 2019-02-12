package org.fluentlenium.adapter.cucumber.integration.tests.driverperfeature.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.core.annotation.Page;

public class SimpleFeatureStep extends BaseTest {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage page2;

    @Given(value = "feature I am on the first page")
    public void step1() {
        goTo(page);
    }

    @When(value = "feature I click on next page")
    public void step2() {
        page.clickLink();
    }

    @Then(value = "feature I am on the second page")
    public void step3() {
        page2.isAt();
    }
}
