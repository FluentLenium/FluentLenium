package org.fluentlenium.adapter.cucumber;

import cucumber.api.Scenario;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.adapter.SharedMutator;

import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.*;

/**
 * Cucumber FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Cucumber Test class.
 */
public class FluentCucumberTest extends FluentTestRunnerAdapter {

    private FluentCucumberTest instance;

    public FluentCucumberTest() {
        instance = FLUENT_TEST.instance();
    }

    /**
     * Creates a new FluentLenium cucumber test.
     */
    public FluentCucumberTest(FluentControlContainer container, Class clazz, SharedMutator sharedMutator) {
        super(container, clazz, sharedMutator);
    }

    public FluentCucumberTest(FluentControlContainer container, SharedMutator sharedMutator) {
        super(container, sharedMutator);
    }

    // It's not allowed by Cucumber JVM to add @Before in the base class.

    /**
     * Initialize a Cucumber scenario inside the adapter.
     *
     * @param scenario Cucumber scenario to initialize
     */
    public void before(Scenario scenario) {
        instance.starting(scenario.getId());
    }

    // It's not allowed by Cucumber JVM to add @After in the base class.

    /**
     * Release a Cucumber scenario from the adapter.
     *
     * @param scenario Cucumber scenario to release
     */
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            instance.failed(scenario.getId());
        }
        instance.finished(scenario.getId());
    }

    public void before() {
        FLUENT_TEST.instance().starting();
    }

    public void after() {
        FLUENT_TEST.instance().finished();
    }

    @Override
    protected FluentControlContainer getControlContainer() {
        return FLUENT_TEST.getControlContainer();
    }
}
