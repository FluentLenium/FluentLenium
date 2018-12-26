package org.fluentlenium.core;

import org.fluentlenium.configuration.ConfigurationFactory;
import org.fluentlenium.configuration.ConfigurationProperties;
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
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

/**
 * Default minimal implementation for {@link FluentContainer}.
 */
public class DefaultFluentContainer implements FluentControl, FluentContainer {

    protected FluentControl control;

    /**
     * Creates a new container.
     */
    public DefaultFluentContainer() {
        // Default constructor
    }

    /**
     * Creates a new container, using given fluent control.
     *
     * @param control fluent control
     */
    public DefaultFluentContainer(FluentControl control) {
        this.control = control;
    }

    private FluentControl getFluentControl() { // NOPMD UnusedPrivateMethod
        return control;
    }

    @Override
    public void initFluent(FluentControl control) {
        this.control = control;
    }

    public void goToInNewTab(String url) {
        getFluentControl().goToInNewTab(url);
    }

    public <T extends org.fluentlenium.core.domain.FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    public <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    public void takeScreenshot() {
        getFluentControl().takeScreenshot();
    }

    public FluentWebElement el(WebElement rawElement) {
        return getFluentControl().el(rawElement);
    }

    public EventsRegistry events() {
        return getFluentControl().events();
    }

    public FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return getFluentControl().asFluentList(elements);
    }

    public FluentWait await() {
        return getFluentControl().await();
    }

    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getFluentControl().getConfigurationFactory();
    }

    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        getFluentControl().setHtmlDumpMode(htmlDumpMode);
    }

    public WindowAction window() {
        return getFluentControl().window();
    }

    public FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return getFluentControl().asFluentList(elements);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    public WebDriver getDriver() {
        return getFluentControl().getDriver();
    }

    public Long getScriptTimeout() {
        return getFluentControl().getScriptTimeout();
    }

    public void setBrowserTimeoutRetries(Integer retriesNumber) {
        getFluentControl().setBrowserTimeoutRetries(retriesNumber);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList);
    }

    public void setImplicitlyWait(Long implicitlyWait) {
        getFluentControl().setImplicitlyWait(implicitlyWait);
    }

    public void setCapabilities(Capabilities capabilities) {
        getFluentControl().setCapabilities(capabilities);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return getFluentControl().newComponentList(componentClass);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return getFluentControl().newFluentList(componentClass, elements);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return getFluentControl().newComponentList(listClass, componentClass);
    }

    public void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        getFluentControl().setConfigurationFactory(configurationFactory);
    }

    public FluentJavascript executeScript(String script, Object... args) {
        return getFluentControl().executeScript(script, args);
    }

    public FluentList<FluentWebElement> $(List<WebElement> rawElements) {
        return getFluentControl().$(rawElements);
    }

    public DriverLifecycle getDriverLifecycle() {
        return getFluentControl().getDriverLifecycle();
    }

    public void setDeleteCookies(Boolean deleteCookies) {
        getFluentControl().setDeleteCookies(deleteCookies);
    }

    public void takeHtmlDump(String fileName) {
        getFluentControl().takeHtmlDump(fileName);
    }

    public Cookie getCookie(String name) {
        return getFluentControl().getCookie(name);
    }

    public FluentWebElement el(By locator, SearchFilter... filters) {
        return getFluentControl().el(locator, filters);
    }

    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getFluentControl().getConfigurationDefaults();
    }

    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return getFluentControl().find(locator, filters);
    }

    public ContainerContext inject(Object container) {
        return getFluentControl().inject(container);
    }

    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return getFluentControl().find(rawElements);
    }

    public Boolean getDeleteCookies() {
        return getFluentControl().getDeleteCookies();
    }

    public Integer getBrowserTimeoutRetries() {
        return getFluentControl().getBrowserTimeoutRetries();
    }

    public FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(elements);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    public String pageSource() {
        return getFluentControl().pageSource();
    }

    public String getWebDriver() {
        return getFluentControl().getWebDriver();
    }

    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return getFluentControl().find(selector, filters);
    }

    public Alert alert() {
        return getFluentControl().alert();
    }

    public FluentList<FluentWebElement> $(String selector, SearchFilter... filters) {
        return getFluentControl().$(selector, filters);
    }

    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        return getFluentControl().newComponent(componentClass, element);
    }

    public FluentList<FluentWebElement> $(By locator, SearchFilter... filters) {
        return getFluentControl().$(locator, filters);
    }

    public Capabilities capabilities() {
        return getFluentControl().capabilities();
    }

    public void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        getFluentControl().setDriverLifecycle(driverLifecycle);
    }

    public <T> T newInstance(Class<T> cls) {
        return getFluentControl().newInstance(cls);
    }

    public String url() {
        return getFluentControl().url();
    }

    public boolean canTakeScreenShot() {
        return getFluentControl().canTakeScreenShot();
    }

    public FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return getFluentControl().newFluentList(elements);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return getFluentControl().newFluentList(componentClass, elements);
    }

    public FluentWebElement el(String selector, SearchFilter... filters) {
        return getFluentControl().el(selector, filters);
    }

    public void setScriptTimeout(Long scriptTimeout) {
        getFluentControl().setScriptTimeout(scriptTimeout);
    }

    public ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext context) {
        return getFluentControl().injectComponent(componentContainer, parentContainer, context);
    }

    public Long getPageLoadTimeout() {
        return getFluentControl().getPageLoadTimeout();
    }

    public Boolean getEventsEnabled() {
        return getFluentControl().getEventsEnabled();
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    public <T> ComponentList asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList);
    }

    public boolean isComponentClass(Class<?> componentClass) {
        return getFluentControl().isComponentClass(componentClass);
    }

    public String getHtmlDumpPath() {
        return getFluentControl().getHtmlDumpPath();
    }

    public FluentWebElement newFluent(WebElement element) {
        return getFluentControl().newFluent(element);
    }

    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return getFluentControl().isComponentListClass(componentListClass);
    }

    public Long getAwaitAtMost() {
        return getFluentControl().getAwaitAtMost();
    }

    public TriggerMode getHtmlDumpMode() {
        return getFluentControl().getHtmlDumpMode();
    }

    public Long getAwaitPollingEvery() {
        return getFluentControl().getAwaitPollingEvery();
    }

    public void setPageLoadTimeout(Long pageLoadTimeout) {
        getFluentControl().setPageLoadTimeout(pageLoadTimeout);
    }

    public void setScreenshotPath(String screenshotPath) {
        getFluentControl().setScreenshotPath(screenshotPath);
    }

    public KeyboardActions keyboard() {
        return getFluentControl().keyboard();
    }

    public Long getImplicitlyWait() {
        return getFluentControl().getImplicitlyWait();
    }

    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        return getFluentControl().find(filters);
    }

    public void takeHtmlDump() {
        getFluentControl().takeHtmlDump();
    }

    public Capabilities getCapabilities() {
        return getFluentControl().getCapabilities();
    }

    public void setRemoteUrl(String remoteUrl) {
        getFluentControl().setRemoteUrl(remoteUrl);
    }

    public void switchTo() {
        getFluentControl().switchTo();
    }

    public FluentList<FluentWebElement> newFluentList() {
        return getFluentControl().newFluentList();
    }

    public void setAwaitAtMost(Long awaitAtMost) {
        getFluentControl().setAwaitAtMost(awaitAtMost);
    }

    public void setBrowserTimeout(Long timeout) {
        getFluentControl().setBrowserTimeout(timeout);
    }

    public void setEventsEnabled(Boolean eventsEnabled) {
        getFluentControl().setEventsEnabled(eventsEnabled);
    }

    public String getCustomProperty(String propertyName) {
        return getFluentControl().getCustomProperty(propertyName);
    }

    public <P extends FluentPage> P goTo(P page) {
        return getFluentControl().goTo(page);
    }

    public void switchTo(FluentWebElement element) {
        getFluentControl().switchTo(element);
    }

    public void setCustomProperty(String key, String value) {
        getFluentControl().setCustomProperty(key, value);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    public void setBaseUrl(String baseUrl) {
        getFluentControl().setBaseUrl(baseUrl);
    }

    public void takeScreenshot(String fileName) {
        getFluentControl().takeScreenshot(fileName);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return getFluentControl().newFluentList(componentClass);
    }

    public void setWebDriver(String webDriver) {
        getFluentControl().setWebDriver(webDriver);
    }

    public FluentJavascript executeAsyncScript(String script, Object... args) {
        return getFluentControl().executeAsyncScript(script, args);
    }

    public void switchToDefault() {
        getFluentControl().switchToDefault();
    }

    public void switchTo(FluentList<? extends FluentWebElement> elements) {
        getFluentControl().switchTo(elements);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    public TriggerMode getScreenshotMode() {
        return getFluentControl().getScreenshotMode();
    }

    public String getScreenshotPath() {
        return getFluentControl().getScreenshotPath();
    }

    public Set<Cookie> getCookies() {
        return getFluentControl().getCookies();
    }

    public Long getBrowserTimeout() {
        return getFluentControl().getBrowserTimeout();
    }

    public FluentWebElement el(SearchFilter... filters) {
        return getFluentControl().el(filters);
    }

    public String getRemoteUrl() {
        return getFluentControl().getRemoteUrl();
    }

    public <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList);
    }

    public MouseActions mouse() {
        return getFluentControl().mouse();
    }

    public void setAwaitPollingEvery(Long awaitPollingEvery) {
        getFluentControl().setAwaitPollingEvery(awaitPollingEvery);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList);
    }

    public String getBaseUrl() {
        return getFluentControl().getBaseUrl();
    }

    public void setScreenshotMode(TriggerMode screenshotMode) {
        getFluentControl().setScreenshotMode(screenshotMode);
    }

    public FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return getFluentControl().newFluentList(elements);
    }

    public void setHtmlDumpPath(String htmlDumpPath) {
        getFluentControl().setHtmlDumpPath(htmlDumpPath);
    }

    public CssSupport css() {
        return getFluentControl().css();
    }

    public void goTo(String url) {
        getFluentControl().goTo(url);
    }

    public FluentList<FluentWebElement> $(SearchFilter... filters) {
        return getFluentControl().$(filters);
    }
}
