package org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.getbean;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/tests/scenario",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class GetBeanRunner {

}
