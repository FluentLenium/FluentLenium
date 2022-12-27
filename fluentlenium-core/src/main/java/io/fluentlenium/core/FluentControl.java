package io.fluentlenium.core;

import io.appium.java_client.AppiumDriver;
import io.fluentlenium.adapter.FluentControlContainer;
import io.fluentlenium.configuration.Configuration;
import io.fluentlenium.configuration.ConfigurationFactory;
import io.fluentlenium.configuration.ConfigurationProperties;
import io.fluentlenium.core.action.InputControl;
import io.fluentlenium.core.action.KeyboardActions;
import io.fluentlenium.core.action.MouseActions;
import io.fluentlenium.core.action.WindowAction;
import io.fluentlenium.core.alert.Alert;
import io.fluentlenium.core.alert.AlertControl;
import io.fluentlenium.core.capabilities.CapabilitiesControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.css.CssControl;
import io.fluentlenium.core.css.CssSupport;
import io.fluentlenium.core.domain.ComponentList;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.events.EventsControl;
import io.fluentlenium.core.events.EventsRegistry;
import io.fluentlenium.core.inject.ContainerContext;
import io.fluentlenium.core.inject.FluentInjectControl;
import io.fluentlenium.core.navigation.NavigationControl;
import io.fluentlenium.core.performance.PerformanceTiming;
import io.fluentlenium.core.performance.PerformanceTimingControl;
import io.fluentlenium.core.script.FluentJavascript;
import io.fluentlenium.core.script.JavascriptControl;
import io.fluentlenium.core.search.SearchControl;
import io.fluentlenium.core.search.SearchFilter;
import io.fluentlenium.core.snapshot.SnapshotControl;
import io.fluentlenium.core.wait.AwaitControl;
import io.fluentlenium.core.wait.FluentWait;
import io.fluentlenium.utils.chromium.ChromiumApi;
import io.fluentlenium.utils.chromium.ChromiumControl;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Global control interface for FluentLenium.
 * <p>
 * It allows to control with a Fluent interface the underlying {@link org.openqa.selenium.WebDriver}.
 */
public interface FluentControl
        extends SearchControl<FluentWebElement>, AwaitControl, InputControl, JavascriptControl, AlertControl, SnapshotControl,
        EventsControl, NavigationControl, SeleniumDriverControl, CssControl, FluentInjectControl, ComponentInstantiator,
        CapabilitiesControl, PerformanceTimingControl, Configuration, ChromiumControl {

    /**
     * Get the control interface container
     *
     * @return control interface container
     */
    FluentControlContainer getControlContainer();

    /**
     * Get Fluent Control
     *
     * @return FluentControl instance
     */
    FluentControl getFluentControl();

    /**
     * Get the test adapter configuration.
     *
     * @return configuration
     */
    Configuration getConfiguration();

    @Override
    default Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getConfiguration().getConfigurationDefaults();
    }

    @Override
    default void setAwaitPollingEvery(Long awaitPollingEvery) {
        getConfiguration().setAwaitPollingEvery(awaitPollingEvery);
    }

    @Override
    default void setCustomProperty(String key, String value) {
        getConfiguration().setCustomProperty(key, value);
    }

    @Override
    default void setBrowserTimeoutRetries(Integer retriesNumber) {
        getConfiguration().setBrowserTimeoutRetries(retriesNumber);
    }

    @Override
    default void setWebDriver(String webDriver) {
        getConfiguration().setWebDriver(webDriver);
    }

    @Override
    default Boolean getDeleteCookies() {
        return getConfiguration().getDeleteCookies();
    }

    @Override
    default void setScreenshotPath(String screenshotPath) {
        getConfiguration().setScreenshotPath(screenshotPath);
    }

    @Override
    default String getBaseUrl() {
        return getConfiguration().getBaseUrl();
    }

    @Override
    default void setAwaitAtMost(Long awaitAtMost) {
        getConfiguration().setAwaitAtMost(awaitAtMost);
    }

    @Override
    default Long getAwaitAtMost() {
        return getConfiguration().getAwaitAtMost();
    }

    @Override
    default TriggerMode getHtmlDumpMode() {
        return getConfiguration().getHtmlDumpMode();
    }

    @Override
    default Long getPageLoadTimeout() {
        return getConfiguration().getPageLoadTimeout();
    }

    @Override
    default void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        getConfiguration().setConfigurationFactory(configurationFactory);
    }

    @Override
    default void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        getConfiguration().setDriverLifecycle(driverLifecycle);
    }

    @Override
    default String getRemoteUrl() {
        return getConfiguration().getRemoteUrl();
    }

    @Override
    default Boolean getEventsEnabled() {
        return getConfiguration().getEventsEnabled();
    }

    @Override
    default void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        getConfiguration().setHtmlDumpMode(htmlDumpMode);
    }

    @Override
    default String getHtmlDumpPath() {
        return getConfiguration().getHtmlDumpPath();
    }

    @Override
    default Long getAwaitPollingEvery() {
        return getConfiguration().getAwaitPollingEvery();
    }

    @Override
    default void setScriptTimeout(Long scriptTimeout) {
        getConfiguration().setScriptTimeout(scriptTimeout);
    }

    @Override
    default Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getConfiguration().getConfigurationFactory();
    }

    @Override
    default String getScreenshotPath() {
        return getConfiguration().getScreenshotPath();
    }

    @Override
    default Integer getBrowserTimeoutRetries() {
        return getConfiguration().getBrowserTimeoutRetries();
    }

    @Override
    default void setBrowserTimeout(Long timeout) {
        getConfiguration().setBrowserTimeout(timeout);
    }

    @Override
    default void setRemoteUrl(String remoteUrl) {
        getConfiguration().setRemoteUrl(remoteUrl);
    }

    @Override
    default String getWebDriver() {
        return getConfiguration().getWebDriver();
    }

    @Override
    default WebDriver getDriver() {
        return getFluentControl().getDriver();
    }

    @Override
    default AppiumDriver getAppiumDriver() {
        return getFluentControl().getAppiumDriver();
    }

    @Override
    default String getCustomProperty(String propertyName) {
        return getConfiguration().getCustomProperty(propertyName);
    }

    @Override
    default void setDeleteCookies(Boolean deleteCookies) {
        getConfiguration().setDeleteCookies(deleteCookies);
    }

    @Override
    default void setEventsEnabled(Boolean eventsEnabled) {
        getConfiguration().setEventsEnabled(eventsEnabled);
    }

    @Override
    default void setHtmlDumpPath(String htmlDumpPath) {
        getConfiguration().setHtmlDumpPath(htmlDumpPath);
    }

    @Override
    default void setPageLoadTimeout(Long pageLoadTimeout) {
        getConfiguration().setPageLoadTimeout(pageLoadTimeout);
    }

    @Override
    default void setScreenshotMode(TriggerMode screenshotMode) {
        getConfiguration().setScreenshotMode(screenshotMode);
    }

    @Override
    default Long getBrowserTimeout() {
        return getConfiguration().getBrowserTimeout();
    }

    @Override
    default void setBaseUrl(String baseUrl) {
        getConfiguration().setBaseUrl(baseUrl);
    }

    @Override
    default DriverLifecycle getDriverLifecycle() {
        return getConfiguration().getDriverLifecycle();
    }

    @Override
    default Long getImplicitlyWait() {
        return getConfiguration().getImplicitlyWait();
    }

    @Override
    default void setImplicitlyWait(Long implicitlyWait) {
        getConfiguration().setImplicitlyWait(implicitlyWait);
    }

    @Override
    default Capabilities getCapabilities() {
        return getConfiguration().getCapabilities();
    }

    @Override
    default Long getScriptTimeout() {
        return getConfiguration().getScriptTimeout();
    }

    @Override
    default void setCapabilities(Capabilities capabilities) {
        getConfiguration().setCapabilities(capabilities);
    }

    @Override
    default TriggerMode getScreenshotMode() {
        return getConfiguration().getScreenshotMode();
    }

    //-------- FluentControl --------

    @Override
    default File takeScreenshot(String fileName) {
        return getFluentControl().takeScreenshot(fileName);
    }

    @Override
    default ChromiumApi getChromiumApi() {
        return getFluentControl().getChromiumApi();
    }

    @Override
    default FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return getFluentControl().asFluentList(elements);
    }

    @Override
    default <P extends FluentPage> P goTo(P page) {
        return getFluentControl().goTo(page);
    }

    @Override
    default FluentJavascript executeScript(String script, Object... args) {
        return getFluentControl().executeScript(script, args);
    }

    @Override
    default <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    @Override
    default void switchToDefault() {
        getFluentControl().switchToDefault();
    }

    @Override
    default <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    @Override
    default FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return getFluentControl().find(locator, filters);
    }

    @Override
    default void goTo(String url) {
        getFluentControl().goTo(url);
    }

    @Override
    default void switchTo() {
        getFluentControl().switchTo();
    }

    @Override
    default void takeHtmlDump() {
        getFluentControl().takeHtmlDump();
    }

    @Override
    default ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext context) {
        return getFluentControl().injectComponent(componentContainer, parentContainer, context);
    }

    @Override
    default void switchTo(FluentList<? extends FluentWebElement> elements) {
        getFluentControl().switchTo(elements);
    }

    @Override
    default boolean canTakeScreenShot() {
        return getFluentControl().canTakeScreenShot();
    }

    @Override
    default <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return getFluentControl().newComponentList(listClass, componentClass);
    }

    @Override
    default <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    @Override
    default <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return getFluentControl().newFluentList(componentClass, elements);
    }

    @Override
    default Capabilities capabilities() {
        return getFluentControl().capabilities();
    }

    @Override
    default <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return getFluentControl().newFluentList(componentClass);
    }

    @Override
    default <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    default FluentList<FluentWebElement> newFluentList() {
        return getFluentControl().newFluentList();
    }

    @Override
    default <T> ComponentList<T> asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    @Override
    default CssSupport css() {
        return getFluentControl().css();
    }

    @Override
    default <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    @Override
    default FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return getFluentControl().find(rawElements);
    }

    @Override
    default void takeHtmlDump(String fileName) {
        getFluentControl().takeHtmlDump(fileName);
    }

    @Override
    default FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return getFluentControl().find(selector, filters);
    }

    @Override
    default <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList);
    }

    @Override
    default <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    @Override
    default <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements);
    }

    @Override
    default void goToInNewTab(String url) {
        getFluentControl().goToInNewTab(url);
    }

    @Override
    default WindowAction window() {
        return getFluentControl().window();
    }

    @Override
    default <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return getFluentControl().newComponentList(componentClass);
    }

    @Override
    default FluentWebElement newFluent(WebElement element) {
        return getFluentControl().newFluent(element);
    }

    @Override
    default Alert alert() {
        return getFluentControl().alert();
    }

    @Override
    default KeyboardActions keyboard() {
        return getFluentControl().keyboard();
    }

    @Override
    default FluentWait await() {
        return getFluentControl().await();
    }

    @Override
    default boolean isComponentClass(Class<?> componentClass) {
        return getFluentControl().isComponentClass(componentClass);
    }

    @Override
    default <T> T newInstance(Class<T> cls) {
        return getFluentControl().newInstance(cls);
    }

    @Override
    default <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asFluentList(componentClass, elements);
    }

    @Override
    default <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements);
    }

    @Override
    default MouseActions mouse() {
        return getFluentControl().mouse();
    }

    @Override
    default ContainerContext inject(Object container) {
        return getFluentControl().inject(container);
    }

    @Override
    default File takeScreenshot() {
        return getFluentControl().takeScreenshot();
    }

    @Override
    default Set<Cookie> getCookies() {
        return getFluentControl().getCookies();
    }

    @Override
    default FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(elements);
    }

    @Override
    default Cookie getCookie(String name) {
        return getFluentControl().getCookie(name);
    }

    @Override
    default <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList);
    }

    @Override
    default FluentList<FluentWebElement> find(SearchFilter... filters) {
        return getFluentControl().find(filters);
    }

    @Override
    default FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return getFluentControl().newFluentList(elements);
    }

    @Override
    default <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    default void switchTo(FluentWebElement element) {
        getFluentControl().switchTo(element);
    }

    @Override
    default String pageSource() {
        return getFluentControl().pageSource();
    }

    @Override
    default boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return getFluentControl().isComponentListClass(componentListClass);
    }

    @Override
    default FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return getFluentControl().newFluentList(elements);
    }

    @Override
    default FluentJavascript executeAsyncScript(String script, Object... args) {
        return getFluentControl().executeAsyncScript(script, args);
    }

    @Override
    default String url() {
        return getFluentControl().url();
    }

    @Override
    default <T> T newComponent(Class<T> componentClass, WebElement element) {
        return getFluentControl().newComponent(componentClass, element);
    }

    @Override
    default <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return getFluentControl().newFluentList(componentClass, elements);
    }

    @Override
    default FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return getFluentControl().asFluentList(elements);
    }

    @Override
    default FluentWebElement el(WebElement rawElement) {
        return getFluentControl().el(rawElement);
    }

    @Override
    default EventsRegistry events() {
        return getFluentControl().events();
    }

    @Override
    default PerformanceTiming performanceTiming() {
        return getFluentControl().performanceTiming();
    }

}
