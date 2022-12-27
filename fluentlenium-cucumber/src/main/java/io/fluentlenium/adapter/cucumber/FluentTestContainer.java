package io.fluentlenium.adapter.cucumber;

import io.fluentlenium.adapter.DefaultFluentControlContainer;
import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.adapter.FluentControlContainer;
import io.fluentlenium.adapter.SharedMutator;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.core.inject.DefaultContainerInstantiator;
import io.fluentlenium.core.inject.FluentInjector;
import io.fluentlenium.adapter.DefaultFluentControlContainer;
import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.adapter.FluentControlContainer;
import io.fluentlenium.adapter.SharedMutator;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.core.inject.DefaultContainerInstantiator;
import io.fluentlenium.core.inject.FluentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Container class for {@link FluentCucumberTest}.
 * <p>
 * It uses Singleton pattern, based on enum, to make sure that all Cucumber steps use the same container.
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
     * <p>
     * If the adapter hasn't been initialized, then initializes the fields of this container,
     * and the {@link FluentAdapter} itself.
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
     * Resets all properties of this container.
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
     * @param clazz class annotated with {@code @RunWith(Cucumber.class)}
     */
    public static void setConfigClass(Class clazz) {
        configClass = clazz;
    }

    protected SharedMutator getSharedMutator() {
        return sharedMutator.get();
    }

    /**
     * Injector used in {@link cucumber.runtime.java.fluentlenium.FluentObjectFactory} for creating instances.
     *
     * @return fluent injector without loaded full FluentControl context
     */
    public FluentInjector injector() {
        return injector.get();
    }

    /**
     * Instantiates {@code @Page} annotated fields in the provided container class,
     * if it has any.
     * <p>
     * The container class is most likely a subclass of {@link FluentCucumberTest}.
     *
     * @param obj container object which contains pages to initialize
     * @see Page
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
