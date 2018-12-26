package org.fluentlenium.core.inject;

import org.fluentlenium.configuration.ConfigurationFactory;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.action.KeyboardActions;
import org.fluentlenium.core.action.MouseActions;
import org.fluentlenium.core.action.WindowAction;
import org.fluentlenium.core.alert.Alert;
import org.fluentlenium.core.css.CssSupport;
import org.fluentlenium.core.domain.ComponentList;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookDefinition;
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
 * Container global FluentLenium control interface.
 */
public class ContainerFluentControl implements FluentControl {
    private final FluentControl adapterControl;

    private ContainerContext context;

    /**
     * Get the underlying control from the test adapter.
     *
     * @return underlying control interface from the test adapter
     */
    public FluentControl getAdapterControl() {
        return adapterControl;
    }

    /**
     * Creates a new container fluent control.
     *
     * @param adapterControl test adapter control interface
     */
    public ContainerFluentControl(FluentControl adapterControl) {
        this(adapterControl, null);
    }

    /**
     * Creates a new container fluent control.
     *
     * @param adapterControl test adapter control interface
     * @param context        container context
     */
    public ContainerFluentControl(FluentControl adapterControl, ContainerContext context) {
        this.adapterControl = adapterControl;
        this.context = context;
    }

    /**
     * Define the container context of this container fluent control interface.
     *
     * @param context container context
     */
    public void setContext(ContainerContext context) {
        this.context = context;
    }

    private <T extends HookControl<?>> T applyHooks(T hookControl) {
        if (context != null) {
            for (HookDefinition hookDefinition : context.getHookDefinitions()) {
                hookControl.withHook(hookDefinition.getHookClass(), hookDefinition.getOptions());
            }
        }
        return hookControl;
    }

    @Override
    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return applyHooks(adapterControl.find(selector, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(String selector, SearchFilter... filters) {
        return applyHooks(adapterControl.$(selector, filters));
    }

    @Override
    public FluentWebElement el(String selector, SearchFilter... filters) {
        return applyHooks(adapterControl.el(selector, filters));
    }

    @Override
    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        return applyHooks(adapterControl.find(filters));
    }

    @Override
    public FluentList<FluentWebElement> $(SearchFilter... filters) {
        return applyHooks(adapterControl.$(filters));
    }

    @Override
    public FluentWebElement el(SearchFilter... filters) {
        return applyHooks(adapterControl.el(filters));
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return applyHooks(adapterControl.find(locator, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, SearchFilter... filters) {
        return applyHooks(adapterControl.$(locator, filters));
    }

    @Override
    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return applyHooks(adapterControl.find(rawElements));
    }

    @Override
    public FluentList<FluentWebElement> $(List<WebElement> rawElements) {
        return applyHooks(adapterControl.$(rawElements));
    }

    @Override
    public FluentWebElement el(WebElement rawElement) {
        return applyHooks(adapterControl.el(rawElement));
    }

    @Override
    public FluentWebElement el(By locator, SearchFilter... filters) {
        return applyHooks(adapterControl.el(locator, filters));
    }

    public void goToInNewTab(String url) {
        getAdapterControl().goToInNewTab(url);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getAdapterControl().asFluentList(componentClass, elements);
    }

    public <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return getAdapterControl().asComponentList(componentClass, elements);
    }

    public void takeScreenshot() {
        getAdapterControl().takeScreenshot();
    }

    public EventsRegistry events() {
        return getAdapterControl().events();
    }

    public FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return getAdapterControl().asFluentList(elements);
    }

    public FluentWait await() {
        return getAdapterControl().await();
    }

    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getAdapterControl().getConfigurationFactory();
    }

    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        getAdapterControl().setHtmlDumpMode(htmlDumpMode);
    }

    public WindowAction window() {
        return getAdapterControl().window();
    }

    public FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return getAdapterControl().asFluentList(elements);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return getAdapterControl().asFluentList(componentClass, elements);
    }

    public WebDriver getDriver() {
        return getAdapterControl().getDriver();
    }

    public Long getScriptTimeout() {
        return getAdapterControl().getScriptTimeout();
    }

    public void setBrowserTimeoutRetries(Integer retriesNumber) {
        getAdapterControl().setBrowserTimeoutRetries(retriesNumber);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return getAdapterControl().newComponentList(componentClass, componentsList);
    }

    public void setImplicitlyWait(Long implicitlyWait) {
        getAdapterControl().setImplicitlyWait(implicitlyWait);
    }

    public void setCapabilities(Capabilities capabilities) {
        getAdapterControl().setCapabilities(capabilities);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return getAdapterControl().newComponentList(componentClass);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return getAdapterControl().newFluentList(componentClass, elements);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return getAdapterControl().newComponentList(listClass, componentClass);
    }

    public void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        getAdapterControl().setConfigurationFactory(configurationFactory);
    }

    public FluentJavascript executeScript(String script, Object... args) {
        return getAdapterControl().executeScript(script, args);
    }

    public DriverLifecycle getDriverLifecycle() {
        return getAdapterControl().getDriverLifecycle();
    }

    public void setDeleteCookies(Boolean deleteCookies) {
        getAdapterControl().setDeleteCookies(deleteCookies);
    }

    public void takeHtmlDump(String fileName) {
        getAdapterControl().takeHtmlDump(fileName);
    }

    public Cookie getCookie(String name) {
        return getAdapterControl().getCookie(name);
    }

    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getAdapterControl().getConfigurationDefaults();
    }

    public ContainerContext inject(Object container) {
        return getAdapterControl().inject(container);
    }

    public Boolean getDeleteCookies() {
        return getAdapterControl().getDeleteCookies();
    }

    public Integer getBrowserTimeoutRetries() {
        return getAdapterControl().getBrowserTimeoutRetries();
    }

    public FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return getAdapterControl().asFluentList(elements);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return getAdapterControl().asComponentList(listClass, componentClass, elements);
    }

    public String pageSource() {
        return getAdapterControl().pageSource();
    }

    public String getWebDriver() {
        return getAdapterControl().getWebDriver();
    }

    public Alert alert() {
        return getAdapterControl().alert();
    }

    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        return getAdapterControl().newComponent(componentClass, element);
    }

    public Capabilities capabilities() {
        return getAdapterControl().capabilities();
    }

    public void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        getAdapterControl().setDriverLifecycle(driverLifecycle);
    }

    public <T> T newInstance(Class<T> cls) {
        return getAdapterControl().newInstance(cls);
    }

    public String url() {
        return getAdapterControl().url();
    }

    public boolean canTakeScreenShot() {
        return getAdapterControl().canTakeScreenShot();
    }

    public FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return getAdapterControl().newFluentList(elements);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return getAdapterControl().newFluentList(componentClass, elements);
    }

    public void setScriptTimeout(Long scriptTimeout) {
        getAdapterControl().setScriptTimeout(scriptTimeout);
    }

    public ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext context) {
        return getAdapterControl().injectComponent(componentContainer, parentContainer, context);
    }

    public Long getPageLoadTimeout() {
        return getAdapterControl().getPageLoadTimeout();
    }

    public Boolean getEventsEnabled() {
        return getAdapterControl().getEventsEnabled();
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return getAdapterControl().asComponentList(listClass, componentClass, elements);
    }

    public <T> ComponentList asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getAdapterControl().asComponentList(componentClass, elements);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return getAdapterControl().newComponentList(listClass, componentClass, componentsList);
    }

    public boolean isComponentClass(Class<?> componentClass) {
        return getAdapterControl().isComponentClass(componentClass);
    }

    public String getHtmlDumpPath() {
        return getAdapterControl().getHtmlDumpPath();
    }

    public FluentWebElement newFluent(WebElement element) {
        return getAdapterControl().newFluent(element);
    }

    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return getAdapterControl().isComponentListClass(componentListClass);
    }

    public Long getAwaitAtMost() {
        return getAdapterControl().getAwaitAtMost();
    }

    public TriggerMode getHtmlDumpMode() {
        return getAdapterControl().getHtmlDumpMode();
    }

    public Long getAwaitPollingEvery() {
        return getAdapterControl().getAwaitPollingEvery();
    }

    public void setPageLoadTimeout(Long pageLoadTimeout) {
        getAdapterControl().setPageLoadTimeout(pageLoadTimeout);
    }

    public void setScreenshotPath(String screenshotPath) {
        getAdapterControl().setScreenshotPath(screenshotPath);
    }

    public KeyboardActions keyboard() {
        return getAdapterControl().keyboard();
    }

    public Long getImplicitlyWait() {
        return getAdapterControl().getImplicitlyWait();
    }

    public void takeHtmlDump() {
        getAdapterControl().takeHtmlDump();
    }

    public Capabilities getCapabilities() {
        return getAdapterControl().getCapabilities();
    }

    public void setRemoteUrl(String remoteUrl) {
        getAdapterControl().setRemoteUrl(remoteUrl);
    }

    public void switchTo() {
        getAdapterControl().switchTo();
    }

    public FluentList<FluentWebElement> newFluentList() {
        return getAdapterControl().newFluentList();
    }

    public void setAwaitAtMost(Long awaitAtMost) {
        getAdapterControl().setAwaitAtMost(awaitAtMost);
    }

    public void setBrowserTimeout(Long timeout) {
        getAdapterControl().setBrowserTimeout(timeout);
    }

    public void setEventsEnabled(Boolean eventsEnabled) {
        getAdapterControl().setEventsEnabled(eventsEnabled);
    }

    public String getCustomProperty(String propertyName) {
        return getAdapterControl().getCustomProperty(propertyName);
    }

    public <P extends org.fluentlenium.core.FluentPage> P goTo(P page) {
        return getAdapterControl().goTo(page);
    }

    public void switchTo(FluentWebElement element) {
        getAdapterControl().switchTo(element);
    }

    public void setCustomProperty(String key, String value) {
        getAdapterControl().setCustomProperty(key, value);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return getAdapterControl().asFluentList(componentClass, elements);
    }

    public void setBaseUrl(String baseUrl) {
        getAdapterControl().setBaseUrl(baseUrl);
    }

    public void takeScreenshot(String fileName) {
        getAdapterControl().takeScreenshot(fileName);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return getAdapterControl().newFluentList(componentClass);
    }

    public void setWebDriver(String webDriver) {
        getAdapterControl().setWebDriver(webDriver);
    }

    public FluentJavascript executeAsyncScript(String script, Object... args) {
        return getAdapterControl().executeAsyncScript(script, args);
    }

    public void switchToDefault() {
        getAdapterControl().switchToDefault();
    }

    public void switchTo(FluentList<? extends FluentWebElement> elements) {
        getAdapterControl().switchTo(elements);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return getAdapterControl().asComponentList(listClass, componentClass, elements);
    }

    public TriggerMode getScreenshotMode() {
        return getAdapterControl().getScreenshotMode();
    }

    public String getScreenshotPath() {
        return getAdapterControl().getScreenshotPath();
    }

    public Set<Cookie> getCookies() {
        return getAdapterControl().getCookies();
    }

    public Long getBrowserTimeout() {
        return getAdapterControl().getBrowserTimeout();
    }

    public String getRemoteUrl() {
        return getAdapterControl().getRemoteUrl();
    }

    public <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return getAdapterControl().asComponentList(componentClass, elements);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return getAdapterControl().newComponentList(componentClass, componentsList);
    }

    public MouseActions mouse() {
        return getAdapterControl().mouse();
    }

    public void setAwaitPollingEvery(Long awaitPollingEvery) {
        getAdapterControl().setAwaitPollingEvery(awaitPollingEvery);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return getAdapterControl().newComponentList(listClass, componentClass, componentsList);
    }

    public String getBaseUrl() {
        return getAdapterControl().getBaseUrl();
    }

    public void setScreenshotMode(TriggerMode screenshotMode) {
        getAdapterControl().setScreenshotMode(screenshotMode);
    }

    public FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return getAdapterControl().newFluentList(elements);
    }

    public void setHtmlDumpPath(String htmlDumpPath) {
        getAdapterControl().setHtmlDumpPath(htmlDumpPath);
    }

    public CssSupport css() {
        return getAdapterControl().css();
    }

    public void goTo(String url) {
        getAdapterControl().goTo(url);
    }
}
