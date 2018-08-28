package org.fluentlenium.adapter.cucumber.integration.java8;

import cucumber.api.CucumberOptions;
import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runner.RunWith;

@RunWith(FluentCucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/java8",
        glue = "org.fluentlenium.adapter.cucumber.integration.java8",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@NotThreadSafe
@FluentConfiguration(webDriver = "htmlunit", driverLifecycle = ConfigurationProperties.DriverLifecycle.DEFAULT)
public class Java8Runner {

}
