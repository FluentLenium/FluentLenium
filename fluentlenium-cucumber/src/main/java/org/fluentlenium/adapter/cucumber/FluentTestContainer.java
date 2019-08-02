package org.fluentlenium.adapter.cucumber;

import org.fluentlenium.adapter.DefaultFluentControlContainer;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.core.FluentDriverHtmlDumper;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.inject.DefaultContainerInstantiator;
import org.fluentlenium.core.inject.FluentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(FluentTestContainer.class);

    private ThreadLocal<FluentAdapter> fluentAdapter;
    private ThreadLocal<FluentControlContainer> controlContainer;
    private ThreadLocal<SharedMutator> sharedMutator;
    private ThreadLocal<FluentInjector> injector;

    private static Class<?> configClass;

    FluentTestContainer() {
        fluentAdapter = new ThreadLocal<>();
        controlContainer = new ThreadLocal<>();
        sharedMutator = new ThreadLocal<>();
        injector = new ThreadLocal<>();
    }

    /**
     * Returns single instance of adapter across all Cucumber steps.
     *
     * @return instance of fluent adapter
     */
    public FluentAdapter instance() {
        if (isNull(fluentAdapter.get())) {
            controlContainer.set(new DefaultFluentControlContainer());
            sharedMutator.set(new FluentCucumberSharedMutator());

            if (nonNull(configClass)) {
                fluentAdapter.set(new FluentCucumberTest(controlContainer.get(), configClass, sharedMutator.get()));
            } else {
                fluentAdapter.set(new FluentCucumberTest(controlContainer.get(), sharedMutator.get()));
            }
            injector.set(new FluentInjector(fluentAdapter.get(), null,
                    new ComponentsManager(fluentAdapter.get()),
                    new DefaultContainerInstantiator(fluentAdapter.get())));
        }
        return fluentAdapter.get();
    }

    /**
     * Reset instance of FluentAdapter stored in container.
     */
    public void reset() {
        sharedMutator.remove();
        controlContainer.remove();
        injector.remove();
        configClass = null;
        fluentAdapter.remove();
    }

    /**
     * Provide control container across different classes.
     *
     * @return control container instance.
     */
    protected FluentControlContainer getControlContainer() {
        if (fluentAdapter.get() == null) {
            instance();
        }
        return controlContainer.get();
    }

    /**
     * Sets config class - needed to enable annotation configuration.
     *
     * @param clazz class annotated with @RunWith(FluentCucumber.class)
     */
    public static void setConfigClass(Class clazz) {
        configClass = clazz;
    }

    /**
     * Returns used inside container SharedMutator
     *
     * @return SharedMutator instance
     */
    protected SharedMutator getSharedMutator() {
        return sharedMutator.get();
    }

    /**
     * Injector used in FluentObjectFactory for creating instances
     *
     * @return fluent injector without loaded full FluentControl context
     */
    public FluentInjector injector() {
        return injector.get();
    }

    /**
     * Creating new instances of pages.
     *
     * @param obj container obj which contains pages to initialize
     */
    public void instantiatePages(Object obj) {

        Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Page.class))
                .forEach(field -> {
                    try {
                        Object instance = injector.get().newInstance(field.getType());
                        field.setAccessible(true);
                        field.set(obj, instance);
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        LOGGER.warn(Arrays.toString(e.getStackTrace()));
                    }
                });
    }
}
