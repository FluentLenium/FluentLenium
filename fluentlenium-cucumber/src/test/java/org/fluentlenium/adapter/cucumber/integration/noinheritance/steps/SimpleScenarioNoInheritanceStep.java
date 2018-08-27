package org.fluentlenium.adapter.cucumber.integration.noinheritance.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import org.fluentlenium.core.annotation.Page;

import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

public class SimpleScenarioNoInheritanceStep {

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
        page.el("a#linkToPage2").click();
    }

    @Then(value = "scenario I am on the second page")
    public void step3() {
        page2.isAt();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        FLUENT_TEST.instance().before(scenario);
    }

    @After
    public void afterScenario(Scenario scenario) {
        FLUENT_TEST.instance().after(scenario);
    }
}
