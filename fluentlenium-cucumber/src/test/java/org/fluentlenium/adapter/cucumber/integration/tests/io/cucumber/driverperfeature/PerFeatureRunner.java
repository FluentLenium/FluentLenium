package org.fluentlenium.adapter.cucumber.integration.tests.io.cucumber.driverperfeature;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import net.jcip.annotations.NotThreadSafe;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:org/fluentlenium/adapter/cucumber/integration/tests/feature",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@NotThreadSafe
public class PerFeatureRunner {

}
