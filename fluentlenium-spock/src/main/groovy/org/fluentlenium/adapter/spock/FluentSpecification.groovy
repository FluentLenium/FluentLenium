package org.fluentlenium.adapter.spock

import io.appium.java_client.AppiumDriver
import org.fluentlenium.configuration.ConfigurationFactory
import org.fluentlenium.configuration.ConfigurationProperties
import org.fluentlenium.core.FluentControl
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.action.KeyboardActions
import org.fluentlenium.core.action.MouseActions
import org.fluentlenium.core.action.WindowAction
import org.fluentlenium.core.alert.Alert
import org.fluentlenium.core.css.CssSupport
import org.fluentlenium.core.domain.ComponentList
import org.fluentlenium.core.events.EventsRegistry
import org.fluentlenium.core.inject.ContainerContext
import org.fluentlenium.core.performance.PerformanceTiming
import org.fluentlenium.core.script.FluentJavascript
import org.fluentlenium.core.wait.FluentWait
import org.fluentlenium.utils.SeleniumVersionChecker
import org.fluentlenium.adapter.FluentTestRunnerAdapter
import org.fluentlenium.adapter.junit.FluentTestRule
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.core.search.SearchFilter
import org.fluentlenium.utils.chromium.ChromiumApi
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.openqa.selenium.By
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Cookie
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import spock.lang.Shared
import spock.lang.Specification

class FluentSpecification extends Specification implements FluentControl {

    @SuppressWarnings("GroovyAccessibility")
    @Shared
    private FluentSpecificationAdapter adapter = new FluentSpecificationAdapter()

    FluentControl getFluentControl() {
        return adapter.getFluentControl()
    }

    def getTestClass() {
        return adapter.getTestClass()
    }

    def getTestMethodName() {
        return adapter.getTestMethodName()
    }

    @Override
    WebDriver getDriver() {
        return getFluentControl().getDriver()
    }

    @Override
    AppiumDriver<?> getAppiumDriver() {
        return getFluentControl().getAppiumDriver()
    }

    @Override
    PerformanceTiming performanceTiming() {
        return getFluentControl().performanceTiming()
    }

    @Override
    void setWebDriver(String webDriver) {
        adapter.setWebDriver(webDriver)
    }

    @Override
    void setBrowserTimeout(Long timeout) {
        adapter.setBrowserTimeout(timeout)
    }

    @Override
    void setBrowserTimeoutRetries(Integer retriesNumber) {
        adapter.setBrowserTimeoutRetries(retriesNumber)
    }

    @Override
    void setRemoteUrl(String remoteUrl) {
        adapter.setRemoteUrl(remoteUrl)
    }

    @Override
    void setCapabilities(Capabilities capabilities) {
        adapter.setCapabilities(capabilities)
    }

    @Override
    void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        adapter.setConfigurationFactory(configurationFactory)
    }

    @Override
    void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        adapter.setDriverLifecycle(driverLifecycle)
    }

    @Override
    void setDeleteCookies(Boolean deleteCookies) {
        adapter.setDeleteCookies(deleteCookies)
    }

    @Override
    void setBaseUrl(String baseUrl) {
        adapter.setBaseUrl(baseUrl)
    }

    @Override
    void setPageLoadTimeout(Long pageLoadTimeout) {
        adapter.setPageLoadTimeout(pageLoadTimeout)
    }

    @Override
    void setImplicitlyWait(Long implicitlyWait) {
        adapter.setImplicitlyWait(implicitlyWait)
    }

    @Override
    void setAwaitAtMost(Long awaitAtMost) {
        adapter.setAwaitAtMost(awaitAtMost)
    }

    @Override
    void setAwaitPollingEvery(Long awaitPollingEvery) {
        adapter.setAwaitPollingEvery(awaitPollingEvery)
    }

    @Override
    void setScriptTimeout(Long scriptTimeout) {
        adapter.setScriptTimeout(scriptTimeout)
    }

    @Override
    void setEventsEnabled(Boolean eventsEnabled) {
        adapter.setEventsEnabled(eventsEnabled)
    }

    @Override
    void setScreenshotPath(String screenshotPath) {
        adapter.setScreenshotPath(screenshotPath)
    }

    @Override
    void setScreenshotMode(TriggerMode screenshotMode) {
        adapter.setScreenshotMode(screenshotMode)
    }

    @Override
    void setHtmlDumpPath(String htmlDumpPath) {
        adapter.setHtmlDumpPath(htmlDumpPath)
    }

    @Override
    void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        adapter.setHtmlDumpMode(htmlDumpMode)
    }

    @Override
    void setCustomProperty(String key, String value) {
        adapter.setCustomProperty(key, value)
    }

    @Override
    String getWebDriver() {
        return getFluentControl().getWebDriver()
    }

    @Override
    String getRemoteUrl() {
        return getFluentControl().getRemoteUrl()
    }

    @Override
    Capabilities getCapabilities() {
        return getFluentControl().getCapabilities()
    }

    @Override
    String getBaseUrl() {
        return getFluentControl().getBaseUrl()
    }

    @Override
    DriverLifecycle getDriverLifecycle() {
        return getFluentControl().getDriverLifecycle()
    }

    @Override
    Long getBrowserTimeout() {
        return getFluentControl().getBrowserTimeout()
    }

    @Override
    Integer getBrowserTimeoutRetries() {
        return getFluentControl().getBrowserTimeoutRetries()
    }

    @Override
    Boolean getDeleteCookies() {
        return getFluentControl().getDeleteCookies()
    }

    @Override
    Long getPageLoadTimeout() {
        return getFluentControl().getPageLoadTimeout()
    }

    @Override
    Long getImplicitlyWait() {
        return getFluentControl().getImplicitlyWait()
    }

    @Override
    Long getScriptTimeout() {
        return getFluentControl().getScriptTimeout()
    }

    @Override
    Long getAwaitAtMost() {
        return getFluentControl().getAwaitAtMost()
    }

    @Override
    Long getAwaitPollingEvery() {
        return getFluentControl().getAwaitPollingEvery()
    }

    @Override
    Boolean getEventsEnabled() {
        return getFluentControl().getEventsEnabled()
    }

    @Override
    String getScreenshotPath() {
        return getFluentControl().getScreenshotPath()
    }

    @Override
    TriggerMode getScreenshotMode() {
        return getFluentControl().getScreenshotMode()
    }

    @Override
    String getHtmlDumpPath() {
        return getFluentControl().getHtmlDumpPath()
    }

    @Override
    TriggerMode getHtmlDumpMode() {
        return getFluentControl().getHtmlDumpMode()
    }

    @Override
    Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getFluentControl().getConfigurationDefaults()
    }

    @Override
    Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getFluentControl().getConfigurationFactory()
    }

    @Override
    String getCustomProperty(String propertyName) {
        return getFluentControl().getCustomProperty(propertyName)
    }

    @Override
    KeyboardActions keyboard() {
        return getFluentControl().keyboard()
    }

    @Override
    MouseActions mouse() {
        return getFluentControl().mouse()
    }

    @Override
    Alert alert() {
        return getFluentControl().alert()
    }

    @Override
    Capabilities capabilities() {
        return getFluentControl().capabilities()
    }

    @Override
    FluentWebElement newFluent(WebElement element) {
        return getFluentControl().newFluent(element)
    }

    @Override
    <T> T newComponent(Class<T> componentClass, WebElement element) {
        return getFluentControl().newComponent(componentClass, element)
    }

    @Override
    FluentList<FluentWebElement> newFluentList() {
        return getFluentControl().newFluentList()
    }

    @Override
    FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return getFluentControl().newFluentList(elements)
    }

    @Override
    FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return getFluentControl().newFluentList(elements)
    }

    @Override
    FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return getFluentControl().asFluentList(elements)
    }

    @Override
    FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(elements)
    }

    @Override
    FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return getFluentControl().asFluentList(elements)
    }

    @Override
    <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return getFluentControl().newFluentList(componentClass)
    }

    @Override
    <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return getFluentControl().newFluentList(componentClass, elements)
    }

    @Override
    <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return getFluentControl().newFluentList(componentClass, elements)
    }

    @Override
    <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asFluentList(componentClass, elements)
    }

    @Override
    <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements)
    }

    @Override
    <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asFluentList(componentClass, elements)
    }

    @Override
    <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return getFluentControl().newComponentList(componentClass)
    }

    @Override
    <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(componentClass, elements)
    }

    @Override
    <T> ComponentList asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements)
    }

    @Override
    <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(componentClass, elements)
    }

    @Override
    <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList)
    }

    @Override
    <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(componentClass, componentsList)
    }

    @Override
    <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return getFluentControl().newComponentList(listClass, componentClass)
    }

    @Override
    <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements)
    }

    @Override
    <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements)
    }

    @Override
    <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return getFluentControl().asComponentList(listClass, componentClass, elements)
    }

    @Override
    <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList)
    }

    @Override
    <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList)
    }

    @Override
    boolean isComponentClass(Class<?> componentClass) {
        return getFluentControl().isComponentClass(componentClass)
    }

    @Override
    boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return getFluentControl().isComponentListClass(componentListClass)
    }

    @Override
    CssSupport css() {
        return getFluentControl().css()
    }

    @Override
    EventsRegistry events() {
        return getFluentControl().events()
    }

    @Override
    ContainerContext inject(Object container) {
        return getFluentControl().inject(container)
    }

    @Override
    ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext context) {
        return getFluentControl().injectComponent(componentContainer, parentContainer, context)
    }

    @Override
    <T> T newInstance(Class<T> cls) {
        return getFluentControl().newInstance(cls)
    }

    @Override
    <P extends FluentPage> P goTo(P page) {
        return getFluentControl().goTo(page)
    }

    @Override
    void goTo(String url) {
        getFluentControl().goTo(url)
    }

    @Override
    void goToInNewTab(String url) {
        getFluentControl().goToInNewTab(url)
    }

    @Override
    void switchTo(FluentList<? extends FluentWebElement> elements) {
        getFluentControl().switchTo(elements)
    }

    @Override
    void switchTo(FluentWebElement element) {
        getFluentControl().switchTo(element)
    }

    @Override
    void switchTo() {
        getFluentControl().switchTo()
    }

    @Override
    void switchToDefault() {
        getFluentControl().switchToDefault()
    }

    @Override
    String pageSource() {
        return getFluentControl().pageSource()
    }

    @Override
    WindowAction window() {
        return getFluentControl().window()
    }

    @Override
    Set<Cookie> getCookies() {
        return getFluentControl().getCookies()
    }

    @Override
    Cookie getCookie(String name) {
        return getFluentControl().getCookie(name)
    }

    @Override
    String url() {
        return getFluentControl().url()
    }

    @Override
    FluentJavascript executeScript(String script, Object... args) {
        return getFluentControl().executeScript(script, args)
    }

    @Override
    FluentJavascript executeAsyncScript(String script, Object... args) {
        return getFluentControl().executeScript(script, args)
    }

    @Override
    FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return getFluentControl().find(rawElements)
    }

    @Override
    FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return getFluentControl().find(selector, filters)
    }

    @Override
    FluentWebElement el(WebElement rawElement) {
        return getFluentControl().el(rawElement)
    }

    @Override
    FluentList<FluentWebElement> find(SearchFilter... filters) {
        return getFluentControl().find(filters)
    }

    @Override
    FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return getFluentControl().find(locator, filters)
    }

    @Override
    void takeHtmlDump() {
        getFluentControl().takeHtmlDump()
    }

    @Override
    void takeHtmlDump(String fileName) {
        getFluentControl().takeHtmlDump(fileName)
    }

    @Override
    boolean canTakeScreenShot() {
        return getFluentControl().canTakeScreenShot()
    }

    @Override
    void takeScreenshot() {
        getFluentControl().takeScreenshot()
    }

    @Override
    void takeScreenshot(String fileName) {
        getFluentControl().takeScreenshot(fileName)
    }

    @Override
    FluentWait await() {
        return getFluentControl().await()
    }

    @Override
    ChromiumApi getChromiumApi() {
        return getFluentControl().getChromiumApi()
    }

    @Rule
    public TestRule watchman = new FluentTestRule(this) {
        @Override
        void starting(Description description) {
            SeleniumVersionChecker.checkSeleniumVersion()
            super.starting(description)
            adapter.specStarting(description.getTestClass(), description.getDisplayName())
        }

        @Override
        void finished(Description description) {
            super.finished(description)
            adapter.specFinished(description.getTestClass(), description.getDisplayName())
        }

        @Override
        void failed(Throwable e, Description description) {
            super.failed(e, description)
            adapter.specFailed(e, description.getTestClass(), description.getDisplayName())
        }
    }

    @ClassRule
    public static TestRule classWatchman = new TestRule() {

        @Override
        Statement apply(Statement base, Description description) {
            return new Statement() {

                @Override
                void evaluate() throws Throwable {
                    try {
                        base.evaluate()
                    } finally {
                        FluentTestRunnerAdapter.afterClass(description.getTestClass())
                    }
                }
            }
        }
    }

}
