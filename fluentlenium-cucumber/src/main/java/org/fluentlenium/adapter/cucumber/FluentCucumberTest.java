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
     * @deprecated when migrating to new Cucumber package structure, use {@link #before(io.cucumber.java8.Scenario)}
     */
    public void before(Scenario scenario) {
        SeleniumVersionChecker.checkSeleniumVersion();
        starting(scenario.getName());
    }

    /**
     * Stopping of FluentCucumberTest adapter
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
     * Initialization of FluentCucumberTestAdapter
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
     * Stopping of FluentCucumberTest adapter
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
