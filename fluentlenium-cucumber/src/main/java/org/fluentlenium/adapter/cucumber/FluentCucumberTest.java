package org.fluentlenium.adapter.cucumber;

import cucumber.api.Scenario;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;

import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

/**
 * Cucumber FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your Cucumber Test class.
 */
public class FluentCucumberTest extends FluentTestRunnerAdapter {

    private final static FluentCucumberTestContainer fluentTest = FluentCucumberTestContainer.FLUENT_TEST;

    /**
     * Initializes context for {@link FluentCucumberTest} and store it in container at
     * {@link FluentCucumberTestContainer} to share state across Cucumber steps.
     */
    public FluentCucumberTest() {
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
     *
     * @param scenario Cucumber scenario
     */
    public void before(Scenario scenario) {
        fluentTest.instance().starting(scenario.getName());
    }

    /**
     * Stopping of FluentCucumberTestAdapter
     *
     * @param scenario Cucumber scenario
     */
    public void after(Scenario scenario) {
        fluentTest.instance().finished(scenario.getName());
    }

    /**
     * Get control container stored in {@link FluentCucumberTestContainer} to avoid problem with state across steps.
     *
     * @return instance of control container
     */
    @Override
    protected FluentControlContainer getControlContainer() {
        return fluentTest.getControlContainer();
    }

    @Override
    public void initFluent(WebDriver webDriver) {
        if (webDriver == null) {
            releaseFluent();
            return;
        }

        if (getControlContainer().getFluentControl() != null) {
            if (getControlContainer().getFluentControl().getDriver() == webDriver) {
                return;
            }
            if (getControlContainer().getFluentControl().getDriver() != null) {
                throw new IllegalStateException("Trying to init a WebDriver, but another one is still running");
            }
        }

        ContainerFluentControl adapterFluentControl = new ContainerFluentControl(new FluentDriver(webDriver, fluentTest.instance(), fluentTest.instance()));
        getControlContainer().setFluentControl(adapterFluentControl);
        ContainerContext context = adapterFluentControl.inject(fluentTest.instance());
        adapterFluentControl.setContext(context);
    }

    /**
     * Initialization of ContainerFluentContext without driver to enable injection of FluentCucumberSteps
     */
    void initFluent() {
        ContainerFluentControl adapterFluentControl = new ContainerFluentControl(new FluentDriver(null, fluentTest.instance(), fluentTest.instance()));
        getControlContainer().setFluentControl(adapterFluentControl);
        ContainerContext context = adapterFluentControl.inject(fluentTest.instance());
        adapterFluentControl.setContext(context);
    }


}
