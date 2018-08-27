package org.fluentlenium.adapter.cucumber;

import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Container class for {@link FluentCucumberTest}.
 * <p>
 * It uses Sinlgeton pattern based on enum to makes sure that all Cucumber steps
 */
public enum FluentCucumberTestContainer  {

    /**
     * Instance of FluentCucumberTestContainer.
     */
    FLUENT_TEST;

    private FluentCucumberTest fluentCucumberTest;
    private FluentControlContainer controlContainer;
    private SharedMutator sharedMutator;
    private Class<?> configClass;

    /**
     * Returns single instance of {@link FluentCucumberTest} across all Cucumber steps.
     *
     * @return instance of {@link FluentCucumberTest}
     */
    public FluentCucumberTest instance() {
        if (isNull(fluentCucumberTest)) {
            controlContainer = new ThreadLocalFluentControlContainer();
            sharedMutator = new FluentCucumberSharedMutator();
            if (nonNull(configClass)) {
                fluentCucumberTest = new FluentCucumberTest(controlContainer, configClass, sharedMutator);
            } else {
                fluentCucumberTest = new FluentCucumberTest(controlContainer, sharedMutator);
            }
        }
        return fluentCucumberTest;
    }

    /**
     * Provide control container across different classes.
     *
     * @return control container instance.
     */
    protected FluentControlContainer getControlContainer() {
        return controlContainer;
    }

    /**
     * Sets config class - needed to enable annotation configuration.
     *
     * @param clazz class annotated with @RunWith(FluentCucumber.class)
     */
    protected void setConfigClass(Class clazz) {
        this.configClass = clazz;
    }

    /**
     * Returns used inside container SharedMutator
     *
     * @return SharedMutator instance
     */
    protected SharedMutator getSharedMutator() {
        return sharedMutator;
    }
}
