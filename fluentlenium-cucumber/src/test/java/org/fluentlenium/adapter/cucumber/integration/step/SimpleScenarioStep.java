package org.fluentlenium.adapter.cucumber.integration.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SimpleScenarioStep extends FluentCucumberTest {

    @Page
    LocalPage page;

    @Page
    LocalPage page2;

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver();
    }

    @Given(value = "scenario I am on the first page")
    public void step1() {
        goTo(page);
    }

    @When(value = "scenario I click on next page")
    public void step2() {
        $("a#linkToPage2").click();
    }

    @Then(value = "scenario I am on the second page")
    public void step3() {
        page2.isAt();
    }

    @Before
    public void before(Scenario scenario) {
        super.before(scenario);
    }

    @After
    public void after(Scenario scenario) {
        super.after(scenario);
    }

}
