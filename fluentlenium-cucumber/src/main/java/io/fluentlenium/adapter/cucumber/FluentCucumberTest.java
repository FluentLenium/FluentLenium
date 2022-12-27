package io.fluentlenium.adapter.cucumber;

import io.cucumber.java.Scenario;
import io.fluentlenium.adapter.FluentControlContainer;
import io.fluentlenium.adapter.FluentTestRunnerAdapter;
import io.fluentlenium.adapter.SharedMutator;
import io.fluentlenium.utils.SeleniumVersionChecker;

/**
 * Cucumber FluentLenium Test Runner Adapter.
 * <p>
 * Extend this class to provide FluentLenium support to your Cucumber Test class. It can be each individual step
 * definitions class, or a base step defs class which is then further extended.
 * <p>
 * This class should also be extended by the class that is for defining the Cucumber Before and After hooks.
 * <p>
 * See <a href="https://fluentlenium.io/docs/test-runners/#cucumber">Cucumber Test Runner</a> documentation for
 * additional examples.
 */
public class FluentCucumberTest extends FluentTestRunnerAdapter {

    /**
     * Initializes context for {@link FluentCucumberTest} and stores it in a
     * {@link FluentTestContainer} to share state across Cucumber steps.
     */
    public FluentCucumberTest() {
        this(FluentTestContainer.FLUENT_TEST.getControlContainer(), FluentTestContainer.FLUENT_TEST.getSharedMutator());
        FluentTestContainer.FLUENT_TEST.instantiatePages(this);
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
     * Initializes this adapter with the provided Scenario.
     * <p>
     * It also performs a Selenium version check to make sure a compatible version is used in the user's project.
     *
     * @param scenario Cucumber scenario
     */
    public void before(Scenario scenario) {
        SeleniumVersionChecker.checkSeleniumVersion();
        starting(scenario.getName());
    }

    /**
     * Stops this adapter, and marks the provided scenario as finished, and also as failed, if necessary,
     * according to its status.
     *
     * @param scenario Cucumber scenario
     */
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            failed(scenario.getName());
        }
        finished(scenario.getName());
    }
}
