package org.fluentlenium.examples.cucumber.java8;

import cucumber.api.CucumberOptions;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.junit.runner.RunWith;

@RunWith(FluentCucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/examples/cucumber",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class FluentCucumberRunner {

}
