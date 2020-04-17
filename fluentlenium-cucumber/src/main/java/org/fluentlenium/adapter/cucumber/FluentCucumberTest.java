package org.fluentlenium.adapter.cucumber;

import io.cucumber.java8.Scenario;
import org.fluentlenium.utils.SeleniumVersionChecker;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.adapter.SharedMutator;

import static org.fluentlenium.adapter.cucumber.FluentTestContainer.FLUENT_TEST;

/**
 * Cucumber FluentLenium Test Runner Adapter.
 * <p>
 * Extend this class to provide FluentLenium support to your Cucumber Test class. It can be each individual step
 * definitions class, or a base step defs class which is then further extended.
 * <p>
 * This class should also be extended by the the class that is for defining the Cucumber Before and After hooks.
 * <p>
 * See <a href="https://fluentlenium.com/docs/test-runners/#cucumber">Cucumber Test Runner</a> documentation for
 * additional examples.
 */
public class FluentCucumberTest extends FluentTestRunnerAdapter {

    /**
     * Initializes context for {@link FluentCucumberTest} and stores it in a
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
     * Initializes this adapter with the provided Scenario.
     * <p>
     * It also performs a Selenium version check to make sure a compatible version is used in the user's project.
     *
     * @param scenario Cucumber scenario
     * @deprecated when migrating to new Cucumber package structure, use {@link #before(io.cucumber.java8.Scenario)}
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
     * @deprecated when migrating to new Cucumber package structure, use {@link #after(io.cucumber.java8.Scenario)}
     */
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            failed(scenario.getName());
        }
        finished(scenario.getName());
    }

    /**
     * Initializes this adapter with the provided Scenario.
     * <p>
     * It also performs a Selenium version check to make sure a compatible version is used in the user's project.
     *
     * @param scenario Cucumber scenario
     * @deprecated when migrating to new Cucumber package structure, use {@link #before(io.cucumber.java.Scenario)}
     */
    @Deprecated
    public void before(io.cucumber.java.Scenario scenario) {
        SeleniumVersionChecker.checkSeleniumVersion();
        starting(scenario.getName());
    }

    /**
     * Stops this adapter, and marks the provided scenario as finished, and also as failed, if necessary,
     * according to its status.
     *
     * @param scenario Cucumber scenario
     * @deprecated when migrating to new Cucumber package structure, use {@link #after(io.cucumber.java.Scenario)}
     */
    @Deprecated
    public void after(io.cucumber.java.Scenario scenario) {
        if (scenario.isFailed()) {
            failed(scenario.getName());
        }
        finished(scenario.getName());
    }
}
