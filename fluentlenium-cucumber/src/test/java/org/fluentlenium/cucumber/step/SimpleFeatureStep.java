package org.fluentlenium.cucumber.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.cucumber.adapter.FluentCucumberTest;
import org.fluentlenium.cucumber.adapter.util.SharedDriver;
import org.fluentlenium.cucumber.page.LocalPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@SharedDriver(type = SharedDriver.SharedType.ONCE)
public class SimpleFeatureStep extends FluentCucumberTest {

    @Page
    LocalPage page;

    @Page
    LocalPage page2;

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver();
    }

    @Given(value = "feature I am on the first page")
    public void step1() {
        goTo(page);
    }

    @When(value = "feature I click on next page")
    public void step2() {
        click("a#linkToPage2");
    }

    @Then(value = "feature I am on the second page")
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
