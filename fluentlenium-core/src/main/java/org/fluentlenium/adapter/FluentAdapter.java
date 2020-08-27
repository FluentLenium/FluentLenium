package org.fluentlenium.adapter;

import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.WebDrivers;
import org.fluentlenium.core.FluentControlImpl;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;

/**
 * Generic adapter to {@link FluentDriver}.
 */
public class FluentAdapter extends FluentControlImpl {

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
    public FluentAdapter(FluentControlContainer controlContainer, Class clazz) {
        super(controlContainer, clazz);
    }

    // We want getDriver to be final.
    public ContainerFluentControl getFluentControl() {
        FluentControlContainer fluentControlContainer = getControlContainer();

        if (fluentControlContainer == null) {
            throw new IllegalStateException("FluentControl is not initialized, WebDriver or Configuration issue");
        } else {
            return (ContainerFluentControl) fluentControlContainer.getFluentControl();
        }
    }

    /**
     * Check if fluent control interface is available from the control interface container.
     *
     * @return true if the fluent control interface is available, false otherwise
     */
    /* default */ boolean isFluentControlAvailable() {
        return getControlContainer().getFluentControl() != null;
    }

    private void setFluentControl(ContainerFluentControl fluentControl) {
        getControlContainer().setFluentControl(fluentControl);
    }

    @Override
    public final WebDriver getDriver() {
        return getFluentControl() == null ? null : getFluentControl().getDriver();
    }

    /**
     * Load a {@link WebDriver} into this adapter.
     * <p>
     * This method should not be called by end user.
     *
     * @param webDriver webDriver to use.
     * @throws IllegalStateException when trying to register a different webDriver that the current one.
     */
    public void initFluent(WebDriver webDriver) {
        if (webDriver == null) {
            releaseFluent();
            return;
        }

        if (getFluentControl() != null) {
            if (getFluentControl().getDriver() == webDriver) {
                return;
            }
            if (getFluentControl().getDriver() != null) {
                throw new IllegalStateException("Trying to init a WebDriver, but another one is still running");
            }
        }

        ContainerFluentControl adapterFluentControl = new ContainerFluentControl(new FluentDriver(webDriver, this, this));
        setFluentControl(adapterFluentControl);
        ContainerContext context = adapterFluentControl.inject(this);
        adapterFluentControl.setContext(context);
    }

    /**
     * Release the current {@link WebDriver} from this adapter.
     * <p>
     * This method should not be called by end user.
     */
    public void releaseFluent() {
        if (getFluentControl() != null) {
            ((FluentDriver) getFluentControl().getAdapterControl()).releaseFluent();
            setFluentControl(null);
        }
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
    public WebDriver newWebDriver() {
        return SharedWebDriverContainer.INSTANCE.newWebDriver(
                getWebDriver(), getCapabilities(), getConfiguration());
    }

}
