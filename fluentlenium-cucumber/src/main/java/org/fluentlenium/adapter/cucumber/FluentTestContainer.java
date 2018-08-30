package org.fluentlenium.adapter.cucumber;

import cucumber.api.Scenario;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.inject.DefaultContainerInstanciator;
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

    private static FluentAdapter fluentAdapter;
    private FluentControlContainer controlContainer;
    private SharedMutator sharedMutator;
    private static Class<?> configClass;
    private FluentInjector injector;
    private static boolean shouldInitialized = false;

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
//            injector = new FluentInjector(fluentAdapter, null,
//                    new ComponentsManager(fluentAdapter), new DefaultContainerInstanciator(fluentAdapter));
        }
        return fluentAdapter;
    }

    /**
     * Provide control container across different classes.
     *
     * @return control container instance.
     */
    protected FluentControlContainer getControlContainer() {
        if(fluentAdapter == null) {
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
        if(configClass != null) {
            configClass = clazz;
        }
    }

    /**
     * Sets config class - needed to enable annotation configuration.
     *
     * @param clazz class annotated with @RunWith(FluentCucumber.class)
     */
    protected static void setConfigWithInjection(Class clazz) {
        configClass = clazz;
        shouldInitialized = true;
    }

    /**
     * Returns used inside container SharedMutator
     *
     * @return SharedMutator instance
     */
    protected SharedMutator getSharedMutator() {
        if(sharedMutator == null) {
            instance();
        }
        return sharedMutator;
    }

    protected FluentInjector injector() {
        if(injector == null){
            instance();
        }
        return injector;
    }

    public static boolean shouldInitialized() {
        return shouldInitialized;
    }

    public static void before(Scenario scenario, Object container) {
        ((FluentCucumberTest) fluentAdapter).before(scenario);
        fluentAdapter.inject(container);
    }

    public static void after(Scenario scenario) {
        ((FluentCucumberTest) fluentAdapter).after(scenario);
        fluentAdapter.releaseFluent();
    }

    public void before() {
        ((FluentCucumberTest) fluentAdapter).before();
    }

    public void after() {
        ((FluentCucumberTest) fluentAdapter).after();
        fluentAdapter.releaseFluent();
    }
}
