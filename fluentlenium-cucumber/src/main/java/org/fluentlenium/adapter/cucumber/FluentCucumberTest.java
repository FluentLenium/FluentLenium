package org.fluentlenium.adapter.cucumber;

import cucumber.api.Scenario;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.adapter.SharedMutator;

import static org.fluentlenium.adapter.cucumber.FluentTestContainer.FLUENT_TEST;

/**
 * Cucumber FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Cucumber Test class.
 */
public class FluentCucumberTest extends FluentTestRunnerAdapter {

    /**
     * Initializes context for {@link FluentCucumberTest} and store it in container at
     * {@link FluentTestContainer} to share state across Cucumber steps.
     */
    public FluentCucumberTest() {
        this(FLUENT_TEST.getControlContainer(), FLUENT_TEST.getSharedMutator());

        FLUENT_TEST.instantiatePages(this);
    }

    /**
     * Constructor used within module. Creates a new FluentLenium cucumber test and points within
     * {@link FluentTestContainer}.
     *
     * @param container     driver container
     * @param clazz         class from which FluentConfiguration annotation will be loaded
     * @param sharedMutator shared mutator
     */
    protected FluentCucumberTest(FluentControlContainer container, Class clazz, SharedMutator sharedMutator) {
        super(container, clazz, sharedMutator);
    }

    /**
     * Constructor used within module. Creates a new FluentLenium cucumber test and points within
     * {@link FluentTestContainer}.
     *
     * @param container     driver container
     * @param sharedMutator shared mutator
     */
    protected FluentCucumberTest(FluentControlContainer container, SharedMutator sharedMutator) {
        super(container, sharedMutator);
    }

    /**
     * Initialization of FluentCucumberTestAdapter
     *
     * @param scenario Cucumber scenario
     */
    public void before(Scenario scenario) {
        starting(scenario.getName());
    }

    /**
     * Initialization of FluentCucumberTest adapter
     */
    void start() {
        starting();
    }

    /**
     * Stopping of FluentCucumberTest adapter
     *
     * @param scenario Cucumber scenario
     */
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            failed(scenario.getName());
        }
        finished(scenario.getName());
    }

    /**
     * Stopping of FluentCucumberTest adapter
     */
    void finish() {
//        TODO find way to pass Scenario or just status of test( if it fails)
//        if (isFailed()) {
//            failed();
//        }
        finished();
    }
}
