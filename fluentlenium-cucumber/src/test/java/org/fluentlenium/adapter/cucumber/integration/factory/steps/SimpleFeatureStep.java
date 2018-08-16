package org.fluentlenium.adapter.cucumber.integration.factory.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM)
public class SimpleFeatureStep extends BaseTest {

    @Given(value = "feature I am on the first page")
    public void step1() {
        page.go();
    }

    @When(value = "feature I click on next page")
    public void step2() {
        page.$("a#linkToPage2").click();
    }

    @Then(value = "feature I am on the second page")
    public void step3() {
        page2.isAt();
    }
}
