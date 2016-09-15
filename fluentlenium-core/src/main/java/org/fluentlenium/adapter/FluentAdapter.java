package org.fluentlenium.adapter;

import lombok.experimental.Delegate;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.ConfigurationFactoryProvider;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.WebDrivers;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Generic adapter to {@link FluentDriver}.
 */
public class FluentAdapter implements FluentControl, ConfigurationProperties {

    private final FluentControlContainer driverContainer;

    private final Configuration configuration = ConfigurationFactoryProvider.newConfiguration(getClass());

    public FluentAdapter() {
        this(new DefaultFluentControlContainer());
    }

    public FluentAdapter(FluentControlContainer driverContainer) {
        this.driverContainer = driverContainer;
    }

    public FluentAdapter(WebDriver webDriver) {
        this(new DefaultFluentControlContainer(), webDriver);
    }

    public FluentAdapter(FluentControlContainer driverContainer, WebDriver webDriver) {
        this.driverContainer = driverContainer;
        initFluent(webDriver);
    }

    @Delegate(types = ConfigurationProperties.class)
    public Configuration getConfiguration() {
        return configuration;
    }

    @Delegate(types = FluentControl.class)
    private ContainerFluentControl getFluentControl() {
        return (ContainerFluentControl) getDriverContainer().getFluentControl();
    }

    boolean isFluentDriverAvailable() {
        return getDriverContainer().getFluentControl() != null;
    }

    private void setFluentControl(ContainerFluentControl fluentControl) {
        getDriverContainer().setFluentControl(fluentControl);
    }

    protected FluentControlContainer getDriverContainer() {
        return driverContainer;
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
                throw new IllegalStateException(
                        "Trying to init a WebDriver, but another one is still running");
            }
        }

        ContainerFluentControl adapterFluentControl = new ContainerFluentControl(new FluentDriver(webDriver, this));
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
        WebDriver webDriver = WebDrivers.INSTANCE.newWebDriver(getWebDriver(), getCapabilities(), this);
        if (Boolean.TRUE.equals(getEventsEnabled())) {
            webDriver = new EventFiringWebDriver(webDriver);
        }
        return webDriver;
    }
}
