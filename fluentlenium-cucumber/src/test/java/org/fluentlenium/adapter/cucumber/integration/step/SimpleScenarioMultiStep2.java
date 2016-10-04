package org.fluentlenium.adapter.cucumber.integration.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM)
public class SimpleScenarioMultiStep2 extends FluentCucumberTest {

    @Page
    private LocalPage page; // NOPMD UsunedPrivateField

    @Page
    private LocalPage page2;

    @Override
    public WebDriver newWebDriver() {
        return new HtmlUnitDriver();
    }

    @When(value = "scenario multi2 I click on next page")
    public void step2() {
        $("a#linkToPage2").click();
    }

    @Then(value = "scenario multi2 I am on the second page")
    public void step3() {
        page2.isAt();
    }

    @Before
    public void before(final Scenario scenario) {
        super.before(scenario);
    }

    @After
    public void after(final Scenario scenario) {
        super.after(scenario);
    }

}
