package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentControlImpl;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;

/**
 * Generic adapter to {@link FluentDriver}.
 */
public class FluentAdapter extends FluentControlImpl implements IFluentAdapter {

    /**
     * Creates a new fluent adapter.
     */
    public FluentAdapter() {
        super();
    }

    /**
     * Creates a new fluent adapter, using given control interface container.
     *
     * @param controlContainer control interface container
     */
    public FluentAdapter(FluentControlContainer controlContainer) {
        super(controlContainer);
    }

    /**
     * Creates a new fluent adapter, using given control interface container.
     *
     * @param controlContainer control interface container
     * @param clazz            class from which annotation configuration will be looked up
     */
    public FluentAdapter(FluentControlContainer controlContainer, Class<?> clazz) {
        super(controlContainer, clazz);
    }

    @Override
    public final WebDriver getDriver() {
        return IFluentAdapter.super.getDriver();
    }

    @Override
    public ContainerFluentControl getFluentControl() {
        return IFluentAdapter.super.getFluentControl();
    }
}
