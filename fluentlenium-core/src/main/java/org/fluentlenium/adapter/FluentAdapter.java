package org.fluentlenium.adapter;

import org.fluentlenium.configuration.*;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.action.KeyboardActions;
import org.fluentlenium.core.action.MouseActions;
import org.fluentlenium.core.action.WindowAction;
import org.fluentlenium.core.alert.Alert;
import org.fluentlenium.core.css.CssSupport;
import org.fluentlenium.core.domain.ComponentList;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.ContainerFluentControl;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generic adapter to {@link FluentDriver}.
 */
public class FluentAdapter implements FluentControl {

    private final FluentControlContainer controlContainer;

    private final Configuration configuration;

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
        configuration = ConfigurationFactoryProvider.newConfiguration(getClass());
    }

    /**
     * Creates a new fluent adapter, using given control interface container.
     *
     * @param controlContainer control interface container
     * @param clazz class from which annotation configuration will be looked up
     */
    public FluentAdapter(FluentControlContainer controlContainer, Class clazz) {
        this.controlContainer = controlContainer;
        configuration = ConfigurationFactoryProvider.newConfiguration(clazz);
    }

    /**
     * Get the test adapter configuration.
     *
     * @return configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

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

    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getConfiguration().getConfigurationDefaults();
    }

    public void setAwaitPollingEvery(Long awaitPollingEvery) {
        getConfiguration().setAwaitPollingEvery(awaitPollingEvery);
    }

    public void setCustomProperty(String key, String value) {
        getConfiguration().setCustomProperty(key, value);
    }

    public void setBrowserTimeoutRetries(Integer retriesNumber) {
        getConfiguration().setBrowserTimeoutRetries(retriesNumber);
    }

    public void setWebDriver(String webDriver) {
        getConfiguration().setWebDriver(webDriver);
    }

    public Boolean getDeleteCookies() {
        return getConfiguration().getDeleteCookies();
    }

    public void setScreenshotPath(String screenshotPath) {
        getConfiguration().setScreenshotPath(screenshotPath);
    }

    public String getBaseUrl() {
        return getConfiguration().getBaseUrl();
    }

    public void setAwaitAtMost(Long awaitAtMost) {
        getConfiguration().setAwaitAtMost(awaitAtMost);
    }

    public Long getAwaitAtMost() {
        return getConfiguration().getAwaitAtMost();
    }

    public TriggerMode getHtmlDumpMode() {
        return getConfiguration().getHtmlDumpMode();
    }

    public Long getPageLoadTimeout() {
        return getConfiguration().getPageLoadTimeout();
    }

    public void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        getConfiguration().setConfigurationFactory(configurationFactory);
    }

    public void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        getConfiguration().setDriverLifecycle(driverLifecycle);
    }

    public String getRemoteUrl() {
        return getConfiguration().getRemoteUrl();
    }

    public Boolean getEventsEnabled() {
        return getConfiguration().getEventsEnabled();
    }

    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        getConfiguration().setHtmlDumpMode(htmlDumpMode);
    }

    public String getHtmlDumpPath() {
        return getConfiguration().getHtmlDumpPath();
    }

    public Long getAwaitPollingEvery() {
        return getConfiguration().getAwaitPollingEvery();
    }

    public void setScriptTimeout(Long scriptTimeout) {
        getConfiguration().setScriptTimeout(scriptTimeout);
    }

    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getConfiguration().getConfigurationFactory();
    }

    public String getScreenshotPath() {
        return getConfiguration().getScreenshotPath();
    }

    public Integer getBrowserTimeoutRetries() {
        return getConfiguration().getBrowserTimeoutRetries();
    }

    public void setBrowserTimeout(Long timeout) {
        getConfiguration().setBrowserTimeout(timeout);
    }

    public void setRemoteUrl(String remoteUrl) {
        getConfiguration().setRemoteUrl(remoteUrl);
    }

    public String getWebDriver() {
        return getConfiguration().getWebDriver();
    }

    public String getCustomProperty(String propertyName) {
        return getConfiguration().getCustomProperty(propertyName);
    }

    public void setDeleteCookies(Boolean deleteCookies) {
        getConfiguration().setDeleteCookies(deleteCookies);
    }

    public void setEventsEnabled(Boolean eventsEnabled) {
        getConfiguration().setEventsEnabled(eventsEnabled);
    }

    public void setHtmlDumpPath(String htmlDumpPath) {
        getConfiguration().setHtmlDumpPath(htmlDumpPath);
    }

    public void setPageLoadTimeout(Long pageLoadTimeout) {
        getConfiguration().setPageLoadTimeout(pageLoadTimeout);
    }

    public void setScreenshotMode(TriggerMode screenshotMode) {
        getConfiguration().setScreenshotMode(screenshotMode);
    }

    public Long getBrowserTimeout() {
        return getConfiguration().getBrowserTimeout();
    }

    public void setBaseUrl(String baseUrl) {
        getConfiguration().setBaseUrl(baseUrl);
    }

    public DriverLifecycle getDriverLifecycle() {
        return getConfiguration().getDriverLifecycle();
    }

    public Long getImplicitlyWait() {
        return getConfiguration().getImplicitlyWait();
    }

    public void setImplicitlyWait(Long implicitlyWait) {
        getConfiguration().setImplicitlyWait(implicitlyWait);
    }

    public Capabilities getCapabilities() {
        return getConfiguration().getCapabilities();
    }

    public Long getScriptTimeout() {
        return getConfiguration().getScriptTimeout();
    }

    public void setCapabilities(Capabilities capabilities) {
        getConfiguration().setCapabilities(capabilities);
    }

    public TriggerMode getScreenshotMode() {
        return getConfiguration().getScreenshotMode();
    }

    public void takeScreenshot(String fileName) {
        getFluentControl().takeScreenshot(fileName);
    }

    public FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return getFluentControl().asFluentList(elements);
    }

    public <P extends org.fluentlenium.core.FluentPage> P goTo(P page) {
        return getFluentControl().goTo(page);
    }

    public FluentJavascript executeScript(String script, Object... args) {
        return getFluentControl().executeScript(script, args);
    }

    public <L extends java.util.List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    public FluentWebElement el(String selector, SearchFilter... filters) {
        return getFluentControl().el(selector, filters);
    }

    public void switchToDefault() {
        getFluentControl().switchToDefault();
    }

    public <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return getFluentControl().find(locator, filters);
    }

    public FluentList<FluentWebElement> $(String selector, SearchFilter... filters) {
        return getFluentControl().$(selector, filters);
    }

    public void goTo(String url) {
        getFluentControl().goTo(url);
    }

    public void switchTo() {
        getFluentControl().switchTo();
    }

    public void takeHtmlDump() {
        getFluentControl().takeHtmlDump();
    }

    public ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext context) {
        return getFluentControl().injectComponent(componentContainer, parentContainer, context);
    }

    public void switchTo(FluentList<? extends FluentWebElement> elements) {
        getFluentControl().switchTo(elements);
    }

    public boolean canTakeScreenShot() {
        return getFluentControl().canTakeScreenShot();
    }

    public <L extends java.util.List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return getFluentControl().newComponentList(listClass, componentClass);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return getFluentControl().newFluentList(componentClass, elements);
    }

    public Capabilities capabilities() {
        return getFluentControl().capabilities();
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return getFluentControl().newFluentList(componentClass);
    }

    public FluentList<FluentWebElement> $(SearchFilter... filters) {
        return getFluentControl().$(filters);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList);
    }

    public FluentList<FluentWebElement> newFluentList() {
        return getFluentControl().newFluentList();
    }

    public <T> ComponentList asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    public CssSupport css() {
        return getFluentControl().css();
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return getFluentControl().find(rawElements);
    }

    public void takeHtmlDump(String fileName) {
        getFluentControl().takeHtmlDump(fileName);
    }

    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return getFluentControl().find(selector, filters);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    public <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    public FluentList<FluentWebElement> $(List<WebElement> rawElements) {
        return getFluentControl().$(rawElements);
    }

    public void goToInNewTab(String url) {
        getFluentControl().goToInNewTab(url);
    }

    public WindowAction window() {
        return getFluentControl().window();
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return getFluentControl().newComponentList(componentClass);
    }

    public FluentWebElement el(SearchFilter... filters) {
        return getFluentControl().el(filters);
    }

    public FluentWebElement newFluent(WebElement element) {
        return getFluentControl().newFluent(element);
    }

    public Alert alert() {
        return getFluentControl().alert();
    }

    public KeyboardActions keyboard() {
        return getFluentControl().keyboard();
    }

    public FluentWait await() {
        return getFluentControl().await();
    }

    public boolean isComponentClass(Class<?> componentClass) {
        return getFluentControl().isComponentClass(componentClass);
    }

    public <T> T newInstance(Class<T> cls) {
        return getFluentControl().newInstance(cls);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    public MouseActions mouse() {
        return getFluentControl().mouse();
    }

    public ContainerContext inject(Object container) {
        return getFluentControl().inject(container);
    }

    public void takeScreenshot() {
        getFluentControl().takeScreenshot();
    }

    public Set<Cookie> getCookies() {
        return getFluentControl().getCookies();
    }

    public FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(elements);
    }

    public Cookie getCookie(String name) {
        return getFluentControl().getCookie(name);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList);
    }

    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        return getFluentControl().find(filters);
    }

    public FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return getFluentControl().newFluentList(elements);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList);
    }

    public void switchTo(FluentWebElement element) {
        getFluentControl().switchTo(element);
    }

    public String pageSource() {
        return getFluentControl().pageSource();
    }

    public FluentWebElement el(By locator, SearchFilter... filters) {
        return getFluentControl().el(locator, filters);
    }

    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return getFluentControl().isComponentListClass(componentListClass);
    }

    public FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return getFluentControl().newFluentList(elements);
    }

    public FluentJavascript executeAsyncScript(String script, Object... args) {
        return getFluentControl().executeAsyncScript(script, args);
    }

    public String url() {
        return getFluentControl().url();
    }

    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        return getFluentControl().newComponent(componentClass, element);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return getFluentControl().newFluentList(componentClass, elements);
    }

    public FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return getFluentControl().asFluentList(elements);
    }

    public FluentList<FluentWebElement> $(By locator, SearchFilter... filters) {
        return getFluentControl().$(locator, filters);
    }

    public FluentWebElement el(WebElement rawElement) {
        return getFluentControl().el(rawElement);
    }

    public EventsRegistry events() {
        return getFluentControl().events();
    }
}
