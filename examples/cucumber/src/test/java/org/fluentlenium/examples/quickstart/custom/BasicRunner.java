package org.fluentlenium.adapter.cucumber.custom;

import cucumber.api.CucumberOptions;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.junit.runner.RunWith;

@RunWith(FluentCucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/examples/quickstart",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class BasicRunner {

}
