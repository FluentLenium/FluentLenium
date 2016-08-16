package org.fluentlenium.adapter;

import lombok.experimental.Delegate;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.WebDrivers;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.FluentDriverControl;
import org.openqa.selenium.WebDriver;

public class FluentAdapter implements FluentDriverControl, Configuration {

    private final DriverContainer driverContainer;

    private String defaultBaseUrl;

    private String screenshotPath;

    private String htmlDumpPath;

    private TriggerMode screenshotMode;

    private TriggerMode htmlDumpMode;

    private Long pageLoadTimeout;

    private Long implicitlyWait;

    private Long scriptTimeout;

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
     */
    public void releaseFluent() {
        if (getFluentDriver() != null) {
            getFluentDriver().releaseFluent();
            setFluentDriver(null);
        }
    }

    /**
     * Override this method to change the driver
     *
     * @return returns WebDriver which is set to FirefoxDriver by default - can be overwritten
     */
    public WebDriver getDefaultDriver() {
        return WebDrivers.newWebDriver("firefox");
    }

    @Override
    public String getDefaultBaseUrl() {
        return defaultBaseUrl;
    }

    @Override
    public void setDefaultBaseUrl(String defaultBaseUrl) {
        this.defaultBaseUrl = defaultBaseUrl;
    }

    @Override
    public Long getPageLoadTimeout() {
        return pageLoadTimeout;
    }

    @Override
    public void setPageLoadTimeout(Long pageLoadTimeout) {
        this.pageLoadTimeout = pageLoadTimeout;
    }

    @Override
    public Long getImplicitlyWait() {
        return implicitlyWait;
    }

    @Override
    public void setImplicitlyWait(Long implicitlyWait) {
        this.implicitlyWait = implicitlyWait;
    }

    @Override
    public Long getScriptTimeout() {
        return scriptTimeout;
    }

    @Override
    public void setScriptTimeout(Long scriptTimeout) {
        this.scriptTimeout = scriptTimeout;
    }

    @Override
    public void setScreenshotPath(String path) {
        this.screenshotPath = path;
    }

    @Override
    public void setHtmlDumpPath(String htmlDumpPath) {
        this.htmlDumpPath = htmlDumpPath;
    }

    @Override
    public void setScreenshotMode(TriggerMode mode) {
        this.screenshotMode = mode;
    }

    @Override
    public TriggerMode getScreenshotMode() {
        return screenshotMode;
    }

    @Override
    public String getScreenshotPath() {
        return screenshotPath;
    }

    @Override
    public String getHtmlDumpPath() {
        return htmlDumpPath;
    }

    @Override
    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        this.htmlDumpMode = htmlDumpMode;
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return htmlDumpMode;
    }
}
