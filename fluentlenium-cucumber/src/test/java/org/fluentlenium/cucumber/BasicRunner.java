package org.fluentlenium.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/cucumber", format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class BasicRunner {


}
