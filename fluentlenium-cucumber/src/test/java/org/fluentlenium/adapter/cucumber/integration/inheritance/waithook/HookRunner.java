package org.fluentlenium.adapter.cucumber.integration.inheritance.waithook;

import cucumber.api.CucumberOptions;
import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.adapter.cucumber.FluentCucumber;
import org.junit.runner.RunWith;

@RunWith(FluentCucumber.class)
@CucumberOptions(glue = "org.fluentlenium.adapter.cucumber.integration.inheritance.waithook.steps",
        features = "classpath:org/fluentlenium/adapter/cucumber/integration/inheritance/waithook",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
@NotThreadSafe
public class HookRunner {

}
