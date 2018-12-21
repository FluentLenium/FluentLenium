package org.fluentlenium.adapter.cucumber;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.inject.DefaultContainerInstantiator;
import org.fluentlenium.core.inject.FluentInjector;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Container class for {@link FluentCucumberTest}.
 * <p>
 * It uses Sinlgeton pattern based on enum to makes sure that all Cucumber steps
 */
public enum FluentTestContainer {

    /**
     * Instance of FluentTestContainer.
     */
    FLUENT_TEST;

    private FluentAdapter fluentAdapter;
    private FluentControlContainer controlContainer;
    private SharedMutator sharedMutator;
    private FluentInjector injector;

    private static Class<?> configClass;

    /**
     * Returns single instance of adapter across all Cucumber steps.
     *
     * @return instance of fluent adapter
     */
    public FluentAdapter instance() {
        if (isNull(fluentAdapter)) {
            controlContainer = new ThreadLocalFluentControlContainer();
            sharedMutator = new FluentCucumberSharedMutator();

            if (nonNull(configClass)) {
                fluentAdapter = new FluentCucumberTest(controlContainer, configClass, sharedMutator);
            } else {
                fluentAdapter = new FluentCucumberTest(controlContainer, sharedMutator);
            }
            injector = new FluentInjector(fluentAdapter, null,
                    new ComponentsManager(fluentAdapter), new DefaultContainerInstantiator(fluentAdapter));
        }
        return fluentAdapter;
    }

    /**
     * Reset instance of FluentAdapter stored in container.
     */
    public void reset() {
        sharedMutator = null;
        controlContainer = null;
        injector = null;
        fluentAdapter = null;
        configClass = null;
    }

    /**
     * Provide control container across different classes.
     *
     * @return control container instance.
     */
    protected FluentControlContainer getControlContainer() {
        if (fluentAdapter == null) {
            instance();
        }
        return controlContainer;
    }

    /**
     * Sets config class - needed to enable annotation configuration.
     *
     * @param clazz class annotated with @RunWith(FluentCucumber.class)
     */
    protected static void setConfigClass(Class clazz) {
        configClass = clazz;
    }

    /**
     * Returns used inside container SharedMutator
     *
     * @return SharedMutator instance
     */
    protected SharedMutator getSharedMutator() {
        if (sharedMutator == null) {
            instance();
        }
        return sharedMutator;
    }

    /**
     * Injector used in {@link FluentObjectFactory} for creating instances
     *
     * @return fluent injector without loaded full FluentControl context
     */
    protected FluentInjector injector() {
        if (injector == null) {
            instance();
        }
        return injector;
    }

    /**
     * Initialization of FluentControl and WebDriver. Using as substitute of
     * alternatives.
     */
    public void before() {
        ((FluentCucumberTest) fluentAdapter).start();
    }

    /**
     * Releasing of FluentControl and WebDriver. Using as substitute of
     * alternatives.
     */
    public void after() {
        ((FluentCucumberTest) fluentAdapter).finish();
    }
}
