package org.fluentlenium.adapter.cucumber.integration.tests.setbean;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.jcip.annotations.NotThreadSafe;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/inheritance/scenario",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@NotThreadSafe
public class SetBeanRunner {

}
