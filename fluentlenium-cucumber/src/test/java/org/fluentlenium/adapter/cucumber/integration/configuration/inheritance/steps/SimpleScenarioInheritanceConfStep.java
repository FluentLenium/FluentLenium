package org.fluentlenium.adapter.cucumber.integration.configuration.inheritance.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.integration.configuration.inheritance.BaseTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import org.fluentlenium.core.annotation.Page;

public class SimpleScenarioInheritanceConfStep extends BaseTest {

    @Page
    protected LocalPage page;

    @Page
    protected LocalPage2 page2;

    @Given(value = "scenario I am on the first page")
    public void step1() {
        goTo(page);
    }

    @When(value = "scenario I click on next page")
    public void step2() {
        page.clickLink();
    }

    @Then(value = "scenario I am on the second page")
    public void step3() {
        page2.isAt();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }

    @After
    public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}
