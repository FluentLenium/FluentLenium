package org.fluentlenium.adapter.testng;

import io.appium.java_client.AppiumDriver;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.configuration.ConfigurationFactory;
import org.fluentlenium.configuration.ConfigurationFactoryProvider;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
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
import org.fluentlenium.core.performance.PerformanceTiming;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.FluentWait;
import org.fluentlenium.utils.chromium.ChromiumApi;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.util.List;
import java.util.Set;

class SpringTestNGControl extends AbstractTestNGSpringContextTests implements FluentControl {

    private final FluentControlContainer controlContainer;
    private final Configuration configuration;

    SpringTestNGControl(FluentControlContainer controlContainer) {
        this.controlContainer = controlContainer;
        this.configuration = ConfigurationFactoryProvider.newConfiguration(getClass());
    }

    SpringTestNGControl(FluentControlContainer controlContainer, Configuration configuration) {
        this.controlContainer = controlContainer;
        this.configuration = configuration;
    }

    @Override
    public FluentControlContainer getControlContainer() {
        return controlContainer;
    }

    @Override
    public FluentControl getFluentControl() {
        return controlContainer.getFluentControl();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public WebDriver getDriver() {
        return getFluentControl().getDriver();
    }

    @Override
    public AppiumDriver<?> getAppiumDriver() {
        return getFluentControl().getAppiumDriver();
    }

    @Override
    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getConfiguration().getConfigurationDefaults();
    }

    @Override
    public void setAwaitPollingEvery(Long awaitPollingEvery) {
        getConfiguration().setAwaitPollingEvery(awaitPollingEvery);
    }

    @Override
    public void setCustomProperty(String key, String value) {
        getConfiguration().setCustomProperty(key, value);
    }

    @Override
    public void setBrowserTimeoutRetries(Integer retriesNumber) {
        getConfiguration().setBrowserTimeoutRetries(retriesNumber);
    }

    @Override
    public void setWebDriver(String webDriver) {
        getConfiguration().setWebDriver(webDriver);
    }

    @Override
    public Boolean getDeleteCookies() {
        return getConfiguration().getDeleteCookies();
    }

    @Override
    public void setScreenshotPath(String screenshotPath) {
        getConfiguration().setScreenshotPath(screenshotPath);
    }

    @Override
    public String getBaseUrl() {
        return getConfiguration().getBaseUrl();
    }

    @Override
    public void setAwaitAtMost(Long awaitAtMost) {
        getConfiguration().setAwaitAtMost(awaitAtMost);
    }

    @Override
    public Long getAwaitAtMost() {
        return getConfiguration().getAwaitAtMost();
    }

    @Override
    public TriggerMode getHtmlDumpMode() {
        return getConfiguration().getHtmlDumpMode();
    }

    @Override
    public Long getPageLoadTimeout() {
        return getConfiguration().getPageLoadTimeout();
    }

    @Override
    public void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        getConfiguration().setConfigurationFactory(configurationFactory);
    }

    @Override
    public void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        getConfiguration().setDriverLifecycle(driverLifecycle);
    }

    @Override
    public String getRemoteUrl() {
        return getConfiguration().getRemoteUrl();
    }

    @Override
    public Boolean getEventsEnabled() {
        return getConfiguration().getEventsEnabled();
    }

    @Override
    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        getConfiguration().setHtmlDumpMode(htmlDumpMode);
    }

    @Override
    public String getHtmlDumpPath() {
        return getConfiguration().getHtmlDumpPath();
    }

    @Override
    public Long getAwaitPollingEvery() {
        return getConfiguration().getAwaitPollingEvery();
    }

    @Override
    public void setScriptTimeout(Long scriptTimeout) {
        getConfiguration().setScriptTimeout(scriptTimeout);
    }

    @Override
    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getConfiguration().getConfigurationFactory();
    }

    @Override
    public String getScreenshotPath() {
        return getConfiguration().getScreenshotPath();
    }

    @Override
    public Integer getBrowserTimeoutRetries() {
        return getConfiguration().getBrowserTimeoutRetries();
    }

    @Override
    public void setBrowserTimeout(Long timeout) {
        getConfiguration().setBrowserTimeout(timeout);
    }

    @Override
    public void setRemoteUrl(String remoteUrl) {
        getConfiguration().setRemoteUrl(remoteUrl);
    }

    @Override
    public String getWebDriver() {
        return getConfiguration().getWebDriver();
    }

    @Override
    public String getCustomProperty(String propertyName) {
        return getConfiguration().getCustomProperty(propertyName);
    }

    @Override
    public void setDeleteCookies(Boolean deleteCookies) {
        getConfiguration().setDeleteCookies(deleteCookies);
    }

    @Override
    public void setEventsEnabled(Boolean eventsEnabled) {
        getConfiguration().setEventsEnabled(eventsEnabled);
    }

    @Override
    public void setHtmlDumpPath(String htmlDumpPath) {
        getConfiguration().setHtmlDumpPath(htmlDumpPath);
    }

    @Override
    public void setPageLoadTimeout(Long pageLoadTimeout) {
        getConfiguration().setPageLoadTimeout(pageLoadTimeout);
    }

    @Override
    public void setScreenshotMode(TriggerMode screenshotMode) {
        getConfiguration().setScreenshotMode(screenshotMode);
    }

    @Override
    public Long getBrowserTimeout() {
        return getConfiguration().getBrowserTimeout();
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        getConfiguration().setBaseUrl(baseUrl);
    }

    @Override
    public DriverLifecycle getDriverLifecycle() {
        return getConfiguration().getDriverLifecycle();
    }

    @Override
    public Long getImplicitlyWait() {
        return getConfiguration().getImplicitlyWait();
    }

    @Override
    public void setImplicitlyWait(Long implicitlyWait) {
        getConfiguration().setImplicitlyWait(implicitlyWait);
    }

    @Override
    public Capabilities getCapabilities() {
        return getConfiguration().getCapabilities();
    }

    @Override
    public Long getScriptTimeout() {
        return getConfiguration().getScriptTimeout();
    }

    @Override
    public void setCapabilities(Capabilities capabilities) {
        getConfiguration().setCapabilities(capabilities);
    }

    @Override
    public TriggerMode getScreenshotMode() {
        return getConfiguration().getScreenshotMode();
    }

    @Override
    public void takeScreenshot(String fileName) {
        getFluentControl().takeScreenshot(fileName);
    }

    @Override
    public final ChromiumApi getChromiumApi() {
        return getFluentControl().getChromiumApi();
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return getFluentControl().asFluentList(elements);
    }

    @Override
    public <P extends FluentPage> P goTo(P page) {
        return getFluentControl().goTo(page);
    }

    @Override
    public FluentJavascript executeScript(String script, Object... args) {
        return getFluentControl().executeScript(script, args);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    @Override
    public void switchToDefault() {
        getFluentControl().switchToDefault();
    }

    @Override
    public <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return getFluentControl().find(locator, filters);
    }

    @Override
    public void goTo(String url) {
        getFluentControl().goTo(url);
    }

    @Override
    public void switchTo() {
        getFluentControl().switchTo();
    }

    @Override
    public void takeHtmlDump() {
        getFluentControl().takeHtmlDump();
    }

    public ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext context) {
        return getFluentControl().injectComponent(componentContainer, parentContainer, context);
    }

    @Override
    public void switchTo(FluentList<? extends FluentWebElement> elements) {
        getFluentControl().switchTo(elements);
    }

    @Override
    public boolean canTakeScreenShot() {
        return getFluentControl().canTakeScreenShot();
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return getFluentControl().newComponentList(listClass, componentClass);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return getFluentControl().newFluentList(componentClass, elements);
    }

    @Override
    public Capabilities capabilities() {
        return getFluentControl().capabilities();
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return getFluentControl().newFluentList(componentClass);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList() {
        return getFluentControl().newFluentList();
    }

    @Override
    public <T> ComponentList asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    @Override
    public CssSupport css() {
        return getFluentControl().css();
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    @Override
    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return getFluentControl().find(rawElements);
    }

    @Override
    public void takeHtmlDump(String fileName) {
        getFluentControl().takeHtmlDump(fileName);
    }

    @Override
    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return getFluentControl().find(selector, filters);
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    @Override
    public <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    @Override
    public void goToInNewTab(String url) {
        getFluentControl().goToInNewTab(url);
    }

    @Override
    public WindowAction window() {
        return getFluentControl().window();
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return getFluentControl().newComponentList(componentClass);
    }

    @Override
    public FluentWebElement newFluent(WebElement element) {
        return getFluentControl().newFluent(element);
    }

    @Override
    public Alert alert() {
        return getFluentControl().alert();
    }

    @Override
    public KeyboardActions keyboard() {
        return getFluentControl().keyboard();
    }

    @Override
    public FluentWait await() {
        return getFluentControl().await();
    }

    @Override
    public boolean isComponentClass(Class<?> componentClass) {
        return getFluentControl().isComponentClass(componentClass);
    }

    @Override
    public <T> T newInstance(Class<T> cls) {
        return getFluentControl().newInstance(cls);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    @Override
    public MouseActions mouse() {
        return getFluentControl().mouse();
    }

    @Override
    public ContainerContext inject(Object container) {
        return getFluentControl().inject(container);
    }

    @Override
    public void takeScreenshot() {
        getFluentControl().takeScreenshot();
    }

    @Override
    public Set<Cookie> getCookies() {
        return getFluentControl().getCookies();
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(elements);
    }

    @Override
    public Cookie getCookie(String name) {
        return getFluentControl().getCookie(name);
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList);
    }

    @Override
    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        return getFluentControl().find(filters);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return getFluentControl().newFluentList(elements);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public void switchTo(FluentWebElement element) {
        getFluentControl().switchTo(element);
    }

    @Override
    public String pageSource() {
        return getFluentControl().pageSource();
    }

    @Override
    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return getFluentControl().isComponentListClass(componentListClass);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return getFluentControl().newFluentList(elements);
    }

    @Override
    public FluentJavascript executeAsyncScript(String script, Object... args) {
        return getFluentControl().executeAsyncScript(script, args);
    }

    @Override
    public String url() {
        return getFluentControl().url();
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        return getFluentControl().newComponent(componentClass, element);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return getFluentControl().newFluentList(componentClass, elements);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return getFluentControl().asFluentList(elements);
    }

    @Override
    public FluentWebElement el(WebElement rawElement) {
        return getFluentControl().el(rawElement);
    }

    @Override
    public EventsRegistry events() {
        return getFluentControl().events();
    }

    @Override
    public PerformanceTiming performanceTiming() {
        return getFluentControl().performanceTiming();
    }

}
