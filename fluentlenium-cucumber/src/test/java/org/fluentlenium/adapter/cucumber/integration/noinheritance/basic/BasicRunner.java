package org.fluentlenium.adapter.cucumber.integration.noinheritance.basic;

import cucumber.api.CucumberOptions;
import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runner.RunWith;

@RunWith(FluentCucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/noinheritance/scenario",
        glue = "org.fluentlenium.adapter.cucumber.integration.noinheritance.steps",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@NotThreadSafe
@FluentConfiguration(webDriver = "htmlunit")
public class BasicRunner {
}
