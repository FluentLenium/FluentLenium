package org.fluentlenium.adapter.cucumber.integration.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM)
public class SimpleScenarioMultiStep1 extends FluentCucumberTest {

    @Page
    LocalPage page;

    @Override
    public WebDriver newWebDriver() {
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
