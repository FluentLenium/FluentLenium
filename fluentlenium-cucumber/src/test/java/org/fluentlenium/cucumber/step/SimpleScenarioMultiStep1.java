package org.fluentlenium.cucumber.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.cucumber.adapter.FluentCucumberTest;
import org.fluentlenium.cucumber.adapter.util.SharedDriver;
import org.fluentlenium.cucumber.page.LocalPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@SharedDriver(type = SharedDriver.SharedType.ONCE)
public class SimpleScenarioMultiStep1 extends FluentCucumberTest {

    @Page
    LocalPage page;

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver();
    }

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        goTo(page);
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
