package org.fluentlenium.core;

import org.openqa.selenium.WebDriver;

/**
 * Default minimal implementation for {@link FluentContainer}.
 */
public class DefaultFluentContainer extends FluentControlImpl implements FluentControl, FluentContainer {

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
    }

    @Override
    public final WebDriver getDriver() {
        return getFluentControl() == null ? null : getFluentControl().getDriver();
    }

    @Override
    public void initFluent(FluentControl control) {
        this.control = control;
    }

}
