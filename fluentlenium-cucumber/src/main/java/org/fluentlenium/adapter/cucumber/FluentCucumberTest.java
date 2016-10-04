package org.fluentlenium.adapter.cucumber;

import cucumber.api.Scenario;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;

/**
 * FluentLenium test adapter for Cucumber.
 */
public class FluentCucumberTest extends FluentTestRunnerAdapter {
    /**
     * Creates a new FluentLenium cucumber test.
     */
    public FluentCucumberTest() {
        super(new FluentCucumberSharedMutator());
    }

    // It's not allowed by Cucumber JVM to add @Before in the base class.

    /**
     * Initialize a Cucumber scenario inside the adapter.
     *
     * @param scenario Cucumber scenario to initialize
     */
    public void before(final Scenario scenario) {
        starting(scenario.getId());
    }

    // It's not allowed by Cucumber JVM to add @After in the base class.

    /**
     * Release a Cucumber scenario from the adapter.
     *
     * @param scenario Cucumber scenario to release
     */
    public void after(final Scenario scenario) {
        if (scenario.isFailed()) {
            failed(scenario.getId());
        }

        finished(scenario.getId());
    }
}
