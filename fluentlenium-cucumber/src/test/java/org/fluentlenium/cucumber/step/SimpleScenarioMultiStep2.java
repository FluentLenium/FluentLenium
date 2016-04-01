package org.fluentlenium.cucumber.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.cucumber.adapter.FluentCucumberTest;
import org.fluentlenium.cucumber.adapter.util.SharedDriver;
import org.fluentlenium.cucumber.page.LocalPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@SharedDriver(type = SharedDriver.SharedType.ONCE)
public class SimpleScenarioMultiStep2 extends FluentCucumberTest {

    @Page
    LocalPage page;

    @Page
    LocalPage page2;

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver();
    }

    @When(value = "scenario multi2 I click on next page")
    public void step2() {
        click("a#linkToPage2");
    }

    @Then(value = "scenario multi2 I am on the second page")
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
