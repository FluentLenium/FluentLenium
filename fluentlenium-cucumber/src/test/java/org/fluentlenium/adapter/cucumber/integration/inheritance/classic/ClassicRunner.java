package org.fluentlenium.adapter.cucumber.integration.inheritance.classic;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.jcip.annotations.NotThreadSafe;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/inheritance/classic",
        glue = "org.fluentlenium.adapter.cucumber.integration.inheritance.classic.steps",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@NotThreadSafe
public class ClassicRunner {

}
