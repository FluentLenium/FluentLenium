package org.fluentlenium.core;

import org.fluentlenium.adapter.DefaultFluentControlContainer;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.ConfigurationFactoryProvider;

/**
 * Default implementation of {@link FluentControl}.
 * <p>
 * It delegates all calls to an underlying {@link FluentControlContainer} containing the {@link FluentDriver}
 * instance, and a {@link Configuration} instance.
 *
 * Do not put any logic here. Consider it as a proxy exposing fluentlenium-core to end user
 */
public class FluentControlImpl implements FluentControl {

    private final FluentControlContainer controlContainer;
    private Configuration configuration;

    public FluentControlImpl() {
        this(new DefaultFluentControlContainer());
    }

    /**
     * The configuration for this control is provided via itself, without having a
     * {@link org.fluentlenium.configuration.FluentConfiguration} annotation specified.
     *
     * @param controlContainer the control interface container
     */
    public FluentControlImpl(FluentControlContainer controlContainer) {
        this.controlContainer = controlContainer;
        this.configuration = ConfigurationFactoryProvider.newConfiguration(getClass());
    }

    /**
     * Creates a new fluent adapter, using given control interface container.
     *
     * @param controlContainer control interface container
     * @param clazz            class from which {@link org.fluentlenium.configuration.FluentConfiguration}
     *                         annotation configuration will be looked up
     */
    public FluentControlImpl(FluentControlContainer controlContainer, Class clazz) {
        this.controlContainer = controlContainer;
        configuration = ConfigurationFactoryProvider.newConfiguration(clazz);
    }

    /**
     * Creates a new fluent adapter using the provided {@code FluentControl} which may be e.g. a {@link FluentDriver}
     * instance.
     *
     * @param fluentControl the fluent control
     */
    public FluentControlImpl(FluentControl fluentControl) {
        this.controlContainer = new DefaultFluentControlContainer();
        controlContainer.setFluentControl(fluentControl);
    }

    @Override
    public FluentControlContainer getControlContainer() {
        return controlContainer;
    }

    @Override
    public FluentControl getFluentControl() {
        return controlContainer.getFluentControl();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
