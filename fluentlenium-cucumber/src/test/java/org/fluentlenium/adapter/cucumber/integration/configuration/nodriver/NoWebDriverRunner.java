package org.fluentlenium.adapter.cucumber.integration.configuration.nodriver;

import cucumber.api.CucumberOptions;
import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.FluentCucumber;

import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runner.RunWith;

@RunWith(FluentCucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/configuration/nodriver",
        glue = "org.fluentlenium.adapter.cucumber.integration.configuration.nodriver.steps",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@NotThreadSafe
public class NoWebDriverRunner {
}
