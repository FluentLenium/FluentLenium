package org.fluentlenium.adapter.cucumber.integration.configuration.noinheritance.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import org.fluentlenium.core.annotation.Page;

public class SimpleScenarioStep {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage2 page2;

    @Given(value = "scenario I am on the first page")
    public void step1() {
        page.go();
    }

    @When(value = "scenario I click on next page")
    public void step2() {
        page.$("a#linkToPage2").click();
    }

    @Then(value = "scenario I am on the second page")
    public void step3() {
        page2.isAt();
    }
}
