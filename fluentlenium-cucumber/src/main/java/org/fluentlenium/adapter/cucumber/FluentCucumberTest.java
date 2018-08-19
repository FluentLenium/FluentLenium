package org.fluentlenium.adapter.cucumber;

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

    /**
     * Creates a new FluentLenium cucumber test.
     */
    public FluentCucumberTest() {
        FLUENT_TEST.instance();
    }

    /**
     * Creates a new FluentLenium cucumber test.
     */
    FluentCucumberTest(FluentControlContainer container, Class clazz, SharedMutator sharedMutator) {
        super(container, clazz, sharedMutator);
    }

    FluentCucumberTest(FluentControlContainer container, SharedMutator sharedMutator) {
        super(container, sharedMutator);
    }

    void before() {
        FLUENT_TEST.instance().starting();
    }

    void after() {
        FLUENT_TEST.instance().finished();
    }

    @Override
    protected FluentControlContainer getControlContainer() {
        return FLUENT_TEST.getControlContainer();
    }
}
