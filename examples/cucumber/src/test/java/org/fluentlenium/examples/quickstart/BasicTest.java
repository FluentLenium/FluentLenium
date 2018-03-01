package org.fluentlenium.examples.quickstart;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/examples/quickstart",
        format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class BasicTest {


}
