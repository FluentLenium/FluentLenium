package org.fluentlenium.adapter.cucumber;

import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.adapter.SharedMutator;

import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

/**
 * Cucumber FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Cucumber Test class.
 */
public class FluentCucumberTest extends FluentTestRunnerAdapter {

    /**
     * Initializes context for {@link FluentCucumberTest} and store it in container at
     * {@link FluentCucumberTestContainer} to share state across Cucumber steps.
     */
    public FluentCucumberTest() {
//        FluentCucumberTest.this.inject(FLUENT_TEST.instance());
    }

    /**
     * Constructor used within module. Creates a new FluentLenium cucumber test and points within
     * {@link FluentCucumberTestContainer}.
     *
     * @param container     driver container
     * @param clazz         class from which FluentConfiguration annotation will be loaded
     * @param sharedMutator shared mutator
     */
    FluentCucumberTest(FluentControlContainer container, Class clazz, SharedMutator sharedMutator) {
        super(container, clazz, sharedMutator);
    }

    /**
     * Constructor used within module. Creates a new FluentLenium cucumber test and points within
     * {@link FluentCucumberTestContainer}.
     *
     * @param container     driver container
     * @param sharedMutator shared mutator
     */
    FluentCucumberTest(FluentControlContainer container, SharedMutator sharedMutator) {
        super(container, sharedMutator);
    }

    /**
     * Initialization of FluentCucumberTestAdapter
     */
    void before() {
        FLUENT_TEST.instance().starting();
    }

    /**
     * Stopping of FluentCucumberTestAdapter
     */
    void after() {
        FLUENT_TEST.instance().finished();
    }

    /**
     * Get control container stored in {@link FluentCucumberTestContainer} to avoid problem with state across steps.
     *
     * @return instance of control container
     */
    @Override
    protected FluentControlContainer getControlContainer() {
        return FLUENT_TEST.getControlContainer();
    }
}
