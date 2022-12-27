package io.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.driverperfeature.steps;

import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.adapter.cucumber.FluentCucumberTest;
import io.fluentlenium.configuration.FluentConfiguration;

import static io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.JVM;

@FluentConfiguration(driverLifecycle = JVM)
public class BaseTest extends FluentCucumberTest {

}
