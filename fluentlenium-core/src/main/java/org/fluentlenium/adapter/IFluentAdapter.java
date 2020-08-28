package org.fluentlenium.adapter;

import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.WebDrivers;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;

public interface IFluentAdapter extends FluentControl {

    /**
     * Check if fluent control interface is available from the control interface container.
     *
     * @return true if the fluent control interface is available, false otherwise
     */
    default boolean isFluentControlAvailable() {
        return getControlContainer().getFluentControl() != null;
    }

    /**
     * Sets FluentControl
     * @param fluentControl to set
     * @return FluentControl
     */
    default FluentControl setFluentControl(ContainerFluentControl fluentControl) {
        getControlContainer().setFluentControl(fluentControl);
        return getControlContainer().getFluentControl();
    }

    /**
     * Load a {@link WebDriver} into this adapter.
     * <p>
     * This method should not be called by end user.
     *
     * @param webDriver webDriver to use.
     * @throws IllegalStateException when trying to register a different webDriver that the current one.
     * @return initialized FluentControl
     */
    default FluentControl initFluent(WebDriver webDriver) {
        if (webDriver == null) {
            releaseFluent();
            return null;
        }

        if (getFluentControl() != null) {
            if (getFluentControl().getDriver() == webDriver) {
                return null;
            }
            if (getFluentControl().getDriver() != null) {
                throw new IllegalStateException("Trying to init a WebDriver, but another one is still running");
            }
        }

        ContainerFluentControl adapterFluentControl = new ContainerFluentControl(new FluentDriver(webDriver, this, this));
        setFluentControl(adapterFluentControl);
        ContainerContext context = adapterFluentControl.inject(this);
        adapterFluentControl.setContext(context);
        return getFluentControl();
    }

    /**
     * Release the current {@link WebDriver} from this adapter.
     * <p>
     * This method should not be called by end user.
     */
    void releaseFluent();

    /**
     * Creates a new {@link WebDriver} instance.
     * <p>
     * This method should not be called by end user, but may be overriden if required.
     * <p>
     * Before overriding this method, you should consider using {@link WebDrivers} registry and configuration
     * {@link ConfigurationProperties#getWebDriver()}.
     * <p>
     * To retrieve the current managed {@link WebDriver}, call {@link #getDriver()} instead.
     *
     * @return A new WebDriver instance.
     * @see #getDriver()
     */
    default WebDriver newWebDriver() {
        return SharedWebDriverContainer.INSTANCE.newWebDriver(
                getWebDriver(), getCapabilities(), getConfiguration());
    }
}
