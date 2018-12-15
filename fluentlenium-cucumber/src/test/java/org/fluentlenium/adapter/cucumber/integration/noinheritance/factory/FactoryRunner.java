package org.fluentlenium.adapter.cucumber.integration.noinheritance.factory;

import cucumber.api.CucumberOptions;
import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runner.RunWith;

@RunWith(FluentCucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/noinheritance/scenario",
        glue = "org.fluentlenium.adapter.cucumber.integration.noinheritance.steps",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@FluentConfiguration(configurationDefaults = TestConfiguration.class)
@NotThreadSafe
public class FactoryRunner {

}
