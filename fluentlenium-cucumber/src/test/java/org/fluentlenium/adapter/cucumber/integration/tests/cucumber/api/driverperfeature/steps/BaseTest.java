package org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.driverperfeature.steps;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.FluentConfiguration;

import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.JVM;

@FluentConfiguration(driverLifecycle = JVM)
public class BaseTest extends FluentCucumberTest {

}
