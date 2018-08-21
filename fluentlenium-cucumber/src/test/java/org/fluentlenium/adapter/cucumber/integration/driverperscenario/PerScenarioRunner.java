package org.fluentlenium.adapter.cucumber.integration.driverperscenario;

import cucumber.api.CucumberOptions;
import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runner.RunWith;

@RunWith(FluentCucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/scenario",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@FluentConfiguration(webDriver = "htmlunit")
@NotThreadSafe
public class PerScenarioRunner {

}
