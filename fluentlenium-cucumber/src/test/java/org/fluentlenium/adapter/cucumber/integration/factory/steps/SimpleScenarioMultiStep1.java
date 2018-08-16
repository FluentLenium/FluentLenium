package org.fluentlenium.adapter.cucumber.integration.factory.steps;

import cucumber.api.java.en.Given;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM)
public class SimpleScenarioMultiStep1 extends BaseTest {

    @Given(value = "scenario multi1 I am on the first page")
    public void step1() {
        page.go();
    }
}
