package org.fluentlenium.adapter.cucumber.integration.driverperfeature;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.FluentConfiguration;

import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.JVM;

@FluentConfiguration(webDriver = "htmlunit", driverLifecycle = JVM)
public class BaseTest extends FluentCucumberTest {

}
