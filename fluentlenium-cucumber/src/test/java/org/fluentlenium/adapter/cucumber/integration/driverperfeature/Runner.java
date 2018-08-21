package org.fluentlenium.adapter.cucumber.integration.driverperfeature;

import cucumber.api.CucumberOptions;
import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runner.RunWith;

import static org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle.JVM;

@RunWith(FluentCucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/feature",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@FluentConfiguration(webDriver = "htmlunit", driverLifecycle = JVM)
@NotThreadSafe
public class Runner {

}
