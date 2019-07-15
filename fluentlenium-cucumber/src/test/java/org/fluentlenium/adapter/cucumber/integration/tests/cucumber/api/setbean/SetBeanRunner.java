package org.fluentlenium.adapter.cucumber.integration.tests.cucumber.api.setbean;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/tests/scenario",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class SetBeanRunner {

}
