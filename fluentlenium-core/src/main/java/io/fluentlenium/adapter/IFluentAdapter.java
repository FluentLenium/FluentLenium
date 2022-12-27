package io.fluentlenium.adapter;

import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import io.fluentlenium.configuration.WebDrivers;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import io.fluentlenium.configuration.ConfigurationProperties;
import io.fluentlenium.configuration.WebDrivers;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentDriver;
import io.fluentlenium.core.inject.ContainerContext;
import io.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

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
    @SuppressWarnings("UnusedReturnValue")
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
     * @param container instance where FluentLenium should inject dependencies
     *
     * @throws IllegalStateException when trying to register a different webDriver that the current one.
     * @return initialized FluentControl
     */
    default FluentControl initFluent(WebDriver webDriver, Object container) {
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
        ContainerContext context = adapterFluentControl.inject(container);
        adapterFluentControl.setContext(context);
        return getFluentControl();
    }

    default FluentControl initFluent(WebDriver webDriver) {
        return initFluent(webDriver, this);
    }

    /**
     * Gets Underlying FluentControlContainer
     *
     * @return fluentControlContainer instance
     */
    @Override
    default ContainerFluentControl getFluentControl() {
        FluentControlContainer fluentControlContainer = getControlContainer();

        if (fluentControlContainer == null) {
            throw new IllegalStateException("FluentControl is not initialized, WebDriver or Configuration issue");
        } else {
            return (ContainerFluentControl) fluentControlContainer.getFluentControl();
        }
    }

    /**
     * Release the current {@link WebDriver} from this adapter.
     * <p>
     * This method should not be called by end user.
     */
    @SuppressWarnings("UnusedReturnValue")
    default boolean releaseFluent() {
        if (getFluentControl() != null) {
            ((FluentDriver) getFluentControl().getAdapterControl()).releaseFluent();
            setFluentControl(null);
            return true;
        }
        return false;
    }

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

    /**
     * returns WebDriver instance
     * @return current webdriver
     */
    @Override
    default WebDriver getDriver() {
        try {
            return Optional.ofNullable(getFluentControl().getDriver())
                    .orElse(null);
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
