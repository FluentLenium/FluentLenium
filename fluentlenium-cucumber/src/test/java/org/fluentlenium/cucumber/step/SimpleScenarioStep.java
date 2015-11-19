package org.fluentlenium.cucumber.step;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.cucumber.adapter.FluentCucumberTest;
import org.fluentlenium.cucumber.adapter.util.SharedDriver;
import org.fluentlenium.cucumber.page.LocalPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@SharedDriver(type = SharedDriver.SharedType.PER_SCENARIO)
public class SimpleScenarioStep extends FluentCucumberTest {

    @Page
    LocalPage page;
    @Page
    LocalPage page2;

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver();
    }

    @Given(value = "scenario I am on the first page")
    public void step1() {
        this.initFluent();
        this.initTest();

        goTo(page);
    }

    @When(value = "scenario I click on next page")
    public void step2() {
		this.initFluent();
		this.initTest();

        click("a#linkToPage2");
    }

    @Then(value = "scenario I am on the second page")
    public void step3() {
		this.initFluent();
        this.initTest();

        page2.isAt();
    }

    @After
    public void after() {
        this.quit();
    }

}
