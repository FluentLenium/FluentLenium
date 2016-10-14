package org.fluentlenium.core.switchto;

import org.fluentlenium.core.alert.AlertImpl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Fluent wrapper for {@link org.openqa.selenium.WebDriver.TargetLocator}.
 *
 * @param <T> self type
 */
public class FluentTargetLocatorImpl<T> implements FluentTargetLocator<T> {

    private final WebDriver.TargetLocator targetLocator;
    private final T self;
    private final ComponentInstantiator componentInstantiator;

    /**
     * Creates a new fluent target locator
     *
     * @param self                  object returned by this target locator
     * @param componentInstantiator component instantiator
     * @param targetLocator         underlying target locator
     */
    public FluentTargetLocatorImpl(final T self, final ComponentInstantiator componentInstantiator,
            final WebDriver.TargetLocator targetLocator) {
        this.self = self;
        this.componentInstantiator = componentInstantiator;
        this.targetLocator = targetLocator;
    }

    @Override
    public T frame(final int index) {
        targetLocator.frame(index);
        return self;
    }

    @Override
    public T frame(final String nameOrId) {
        targetLocator.frame(nameOrId);
        return self;
    }

    @Override
    public T frame(final WebElement frameElement) {
        targetLocator.frame(frameElement);
        return self;
    }

    @Override
    public T frame(final FluentWebElement frameElement) {
        return frame(frameElement.getElement());
    }

    @Override
    public T parentFrame() {
        targetLocator.parentFrame();
        return self;
    }

    @Override
    public T window(final String nameOrHandle) {
        targetLocator.window(nameOrHandle);
        return self;
    }

    @Override
    public T defaultContent() {
        targetLocator.defaultContent();
        return self;
    }

    @Override
    public FluentWebElement activeElement() {
        final WebElement webElement = targetLocator.activeElement();
        return componentInstantiator.newFluent(webElement);
    }

    @Override
    public AlertImpl alert() {
        final org.openqa.selenium.Alert alert = targetLocator.alert();
        return new AlertImpl(alert);
    }
}
