package org.fluentlenium.adapter.cucumber.integration.factory.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage;
import org.fluentlenium.adapter.cucumber.integration.page.LocalPage2;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM)
public class SimpleScenarioMultiStep2 extends BaseTest {


    @When(value = "scenario multi2 I click on next page")
    public void step2() {
        page.$("a#linkToPage2").click();
    }

    @Then(value = "scenario multi2 I am on the second page")
    public void step3() {
        page2.isAt();
    }

}
