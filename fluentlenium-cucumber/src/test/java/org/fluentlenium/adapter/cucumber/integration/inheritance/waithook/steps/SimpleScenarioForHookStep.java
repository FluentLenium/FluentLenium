package org.fluentlenium.adapter.cucumber.integration.inheritance.waithook.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.inheritance.waithook.page.LocalWithHookPage;
import org.fluentlenium.adapter.cucumber.integration.inheritance.waithook.page.LocalWithHookPage2;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;

@FluentConfiguration(webDriver = "htmlunit")
public class SimpleScenarioForHookStep extends FluentCucumberTest {

    @Page
    private LocalWithHookPage page;

    @Page
    private LocalWithHookPage2 page2;

    @Given(value = "scenario I am on the first Wait hook page")
    public void step1() {
        page.go();
    }

    @When(value = "scenario I click on next page")
    public void step2() {
        page.clickLink();
    }

    @Then(value = "scenario I am on the second Wait hook page")
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
