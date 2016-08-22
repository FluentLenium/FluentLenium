package org.fluentlenium.adapter;

import lombok.experimental.Delegate;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.ConfigurationFactoryProvider;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.WebDrivers;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.FluentDriverControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Generic adapter to {@link FluentDriver}.
 */
public class FluentAdapter implements FluentDriverControl, ConfigurationProperties {

    private final DriverContainer driverContainer;

    private final Configuration configuration = ConfigurationFactoryProvider.newConfiguration(getClass());

    public FluentAdapter() {
        this(new DefaultDriverContainer());
    }

    public FluentAdapter(DriverContainer driverContainer) {
        this.driverContainer = driverContainer;
    }

    public FluentAdapter(WebDriver webDriver) {
        this(new DefaultDriverContainer(), webDriver);
    }

    public FluentAdapter(DriverContainer driverContainer, WebDriver webDriver) {
        this.driverContainer = driverContainer;
        initFluent(webDriver);
    }

    @Delegate(types = ConfigurationProperties.class)
    public Configuration getConfiguration() {
        return configuration;
    }

    @Delegate(types = FluentDriverControl.class)
    private FluentDriver getFluentDriver() {
        return getDriverContainer().getFluentDriver();
    }

    boolean isFluentDriverAvailable() {
        return getDriverContainer().getFluentDriver() != null;
    }

    private void setFluentDriver(FluentDriver driver) {
        getDriverContainer().setFluentDriver(driver);
    }

    protected DriverContainer getDriverContainer() {
        return driverContainer;
    }

    /**
     * Load a {@link WebDriver} into this adapter.
     *
     * This method should not be called by end user.
     *
     * @param webDriver webDriver to use.
     * @return adapter
     * @throws IllegalStateException when trying to register a different webDriver that the current one.
     */
    public void initFluent(WebDriver webDriver) {
        if (webDriver == null) {
            releaseFluent();
            return;
        }

        if (getFluentDriver() != null) {
            if (getFluentDriver().getDriver() == webDriver) {
                return;
            }
            if (getFluentDriver().getDriver() != null) {
                throw new IllegalStateException(
                        "Trying to init a WebDriver, but another one is still running");
            }
        }

        FluentDriver fluentDriver = new FluentDriver(webDriver, this);
        setFluentDriver(fluentDriver);
        fluentDriver.inject(this);
    }

    /**
     * Release the current {@link WebDriver} from this adapter.
     *
     * This method should not be called by end user.
     */
    public void releaseFluent() {
        if (getFluentDriver() != null) {
            getFluentDriver().releaseFluent();
            setFluentDriver(null);
        }
    }

    /**
     * @return A new WebDriver instance.
     * @see #getDriver()
     * @deprecated Override {@link #newWebDriver()} instead, or consider using {@link ConfigurationProperties#getWebDriver()}.
     */
    @Deprecated
    public WebDriver getDefaultDriver() {
        WebDriver webDriver = WebDrivers.INSTANCE.newWebDriver(getWebDriver(), getCapabilities());
        if (Boolean.TRUE.equals(getEventsEnabled())) {
            webDriver = new EventFiringWebDriver(webDriver);
        }
        return webDriver;
    }

    /**
     * Creates a new {@link WebDriver} instance.
     *
     * This method should not be called by end user, but may be overriden if required.
     *
     * Before overriding this method, you should consider using {@link WebDrivers} registry and configuration
     * {@link ConfigurationProperties#getWebDriver()}.
     *
     * To retrieve the current managed {@link WebDriver}, call {@link #getDriver()} instead.
     *
     * @return A new WebDriver instance.
     * @see #getDriver()
     */
    public WebDriver newWebDriver() {
        return getDefaultDriver();
    }
}
