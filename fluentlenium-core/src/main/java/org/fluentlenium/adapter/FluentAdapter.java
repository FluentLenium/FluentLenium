package org.fluentlenium.adapter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.ConfigurationFactoryProvider;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.WebDrivers;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.SeleniumDriverControl;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import lombok.experimental.Delegate;

/**
 * Generic adapter to {@link FluentDriver}.
 */
public class FluentAdapter implements FluentControl {

    private final FluentControlContainer controlContainer;

    private final Configuration configuration = ConfigurationFactoryProvider.newConfiguration(getClass());

    private static final Set<String> IGNORED_EXCEPTIONS = Stream.of(
                "org.junit.internal.AssumptionViolatedException",
                "org.testng.SkipException")
            .collect(Collectors.toSet());

    /**
     * Creates a new fluent adapter.
     */
    public FluentAdapter() {
        this(new DefaultFluentControlContainer());
    }

    /**
     * Creates a new fluent adapter, using given control interface container.
     *
     * @param controlContainer control interface container
     */
    public FluentAdapter(FluentControlContainer controlContainer) {
        this.controlContainer = controlContainer;
    }

    /**
     * Get the test adapter configuration.
     *
     * @return configuration
     */
    @Delegate
    public Configuration getConfiguration() {
        return configuration;
    }

    @Delegate(types = FluentControl.class, excludes = {SeleniumDriverControl.class, Configuration.class})
    // We want getDriver to be final.
    private ContainerFluentControl getFluentControl() {
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
     * Get the control interface container
     *
     * @return control interface container
     */
    protected FluentControlContainer getControlContainer() {
        return controlContainer;
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
        WebDriver webDriver = WebDrivers.INSTANCE.newWebDriver(getWebDriver(), getCapabilities(), this);
        if (Boolean.TRUE.equals(getEventsEnabled())) {
            webDriver = new EventFiringWebDriver(webDriver);
        }
        return webDriver;
    }

    /**
     * Checks if the exception should be ignored and not reported as a test case fail
     *
     * @param e - the exception to check is it defined in ignored exceptions set
     * @return boolean
     */
    boolean isIgnoredException(Throwable e) {
        if (e == null) {
            return false;
        }

        Class clazz = e.getClass();
        do {
            if (IGNORED_EXCEPTIONS.contains(clazz.getName())) {
                return true;
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);

        return false;
    }
}
