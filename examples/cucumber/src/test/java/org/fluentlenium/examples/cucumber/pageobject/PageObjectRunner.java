package org.fluentlenium.examples.cucumber.pageobject;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/examples/cucumber",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class PageObjectRunner {
}
