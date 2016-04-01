package org.fluentlenium.cucumber.adapter;

import cucumber.api.Scenario;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.adapter.util.DefaultCookieStrategyReader;

public class FluentCucumberTest extends FluentTestRunnerAdapter {
    public FluentCucumberTest() {
        super(new CucumberSharedDriverStrategyReader(), new DefaultCookieStrategyReader());
    }

    // It's not allowed by Cucumber JVM to add @Before in the base class.
    public void before(Scenario scenario) {
        starting(scenario.getName());
    }

    // It's not allowed by Cucumber JVM to add @After in the base class.
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            failed(scenario.getName());
        }

        finished(scenario.getName());
    }

    // FluentTestRunnerAdapter#releaseSharedDriver is not called. I don't know when to call it, sadly ...
}
