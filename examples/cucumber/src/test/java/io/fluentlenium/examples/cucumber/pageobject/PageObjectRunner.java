package io.fluentlenium.examples.cucumber.pageobject;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/examples/cucumber",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class PageObjectRunner {
}
