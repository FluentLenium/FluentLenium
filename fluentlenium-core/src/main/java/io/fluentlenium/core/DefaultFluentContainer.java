package io.fluentlenium.core;

import org.openqa.selenium.WebDriver;

/**
 * Default minimal implementation for {@link FluentContainer}.
 */
public class DefaultFluentContainer extends FluentControlImpl implements FluentContainer {

    protected FluentControl control;

    /**
     * Creates a new container.
     */
    public DefaultFluentContainer() {
        super();
    }

    /**
     * Creates a new container, using given fluent control.
     *
     * @param control fluent control
     */
    public DefaultFluentContainer(FluentControl control) {
        super(control);
        this.control = control;
    }

    @Override
    public FluentControl getFluentControl() {
        return control;
    }

    @Override
    public void initFluent(FluentControl control) {
        this.control = control;
    }

    @Override
    public final WebDriver getDriver() {
        return getFluentControl() == null ? null : getFluentControl().getDriver();
    }
}
