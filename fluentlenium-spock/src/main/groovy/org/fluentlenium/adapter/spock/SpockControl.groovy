package org.fluentlenium.adapter.spock

import io.appium.java_client.AppiumDriver
import org.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.configuration.Configuration
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
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.core.events.EventsRegistry
import org.fluentlenium.core.inject.ContainerContext
import org.fluentlenium.core.performance.PerformanceTiming
import org.fluentlenium.core.script.FluentJavascript
import org.fluentlenium.core.search.SearchFilter
import org.fluentlenium.core.wait.FluentWait
import org.fluentlenium.utils.chromium.ChromiumApi
import org.openqa.selenium.By
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Cookie
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import spock.lang.Specification

import java.lang.annotation.Annotation

class SpockControl extends Specification implements FluentControl {

    public SpockAdapter adapter = new SpockAdapter()

    FluentControl getFluentControl() {
        return adapter.getFluentControl()
    }

    Configuration getConfig() {
        return adapter.getConfiguration()
    }

    def getTestClass() {
        return adapter.getTestClass()
    }

    def getTestMethodName() {
        return adapter.getTestMethodName()
    }

    protected <T extends Annotation> T getClassAnnotation(Class<T> annotation) {
        def anno = specificationContext.currentSpec.getAnnotation(annotation)

        if (anno == null) {
            throw new AnnotationNotFoundException();
        }

        return anno
    }

    protected <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        def anno = specificationContext.currentFeature.featureMethod.getAnnotation(annotation)

        if (anno == null) {
            throw new AnnotationNotFoundException();
        }

        return anno
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
        getConfig().setWebDriver(webDriver)
    }

    @Override
    void setBrowserTimeout(Long timeout) {
        getConfig().setBrowserTimeout(timeout)
    }

    @Override
    void setBrowserTimeoutRetries(Integer retriesNumber) {
        getConfig().setBrowserTimeoutRetries(retriesNumber)
    }

    @Override
    void setRemoteUrl(String remoteUrl) {
        getConfig().setRemoteUrl(remoteUrl)
    }

    @Override
    void setCapabilities(Capabilities capabilities) {
        getConfig().setCapabilities(capabilities)
    }

    @Override
    void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        getConfig().setConfigurationFactory(configurationFactory)
    }

    @Override
    void setDriverLifecycle(DriverLifecycle driverLifecycle) {
        getConfig().setDriverLifecycle(driverLifecycle)
    }

    @Override
    void setDeleteCookies(Boolean deleteCookies) {
        getConfig().setDeleteCookies(deleteCookies)
    }

    @Override
    void setBaseUrl(String baseUrl) {
        getConfig().setBaseUrl(baseUrl)
    }

    @Override
    void setPageLoadTimeout(Long pageLoadTimeout) {
        getConfig().setPageLoadTimeout(pageLoadTimeout)
    }

    @Override
    void setImplicitlyWait(Long implicitlyWait) {
        getConfig().setImplicitlyWait(implicitlyWait)
    }

    @Override
    void setAwaitAtMost(Long awaitAtMost) {
        getConfig().setAwaitAtMost(awaitAtMost)
    }

    @Override
    void setAwaitPollingEvery(Long awaitPollingEvery) {
        getConfig().setAwaitPollingEvery(awaitPollingEvery)
    }

    @Override
    void setScriptTimeout(Long scriptTimeout) {
        getConfig().setScriptTimeout(scriptTimeout)
    }

    @Override
    void setEventsEnabled(Boolean eventsEnabled) {
        getConfig().setEventsEnabled(eventsEnabled)
    }

    @Override
    void setScreenshotPath(String screenshotPath) {
        getConfig().setScreenshotPath(screenshotPath)
    }

    @Override
    void setScreenshotMode(TriggerMode screenshotMode) {
        getConfig().setScreenshotMode(screenshotMode)
    }

    @Override
    void setHtmlDumpPath(String htmlDumpPath) {
        getConfig().setHtmlDumpPath(htmlDumpPath)
    }

    @Override
    void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        getConfig().setHtmlDumpMode(htmlDumpMode)
    }

    @Override
    void setCustomProperty(String key, String value) {
        getConfig().setCustomProperty(key, value)
    }

    @Override
    String getWebDriver() {
        return getConfig().getWebDriver()
    }

    @Override
    String getRemoteUrl() {
        return getConfig().getRemoteUrl()
    }

    @Override
    Capabilities getCapabilities() {
        return getConfig().getCapabilities()
    }

    @Override
    String getBaseUrl() {
        return getConfig().getBaseUrl()
    }

    @Override
    DriverLifecycle getDriverLifecycle() {
        return getConfig().getDriverLifecycle()
    }

    @Override
    Long getBrowserTimeout() {
        return getConfig().getBrowserTimeout()
    }

    @Override
    Integer getBrowserTimeoutRetries() {
        return getConfig().getBrowserTimeoutRetries()
    }

    @Override
    Boolean getDeleteCookies() {
        return getConfig().getDeleteCookies()
    }

    @Override
    Long getPageLoadTimeout() {
        return getConfig().getPageLoadTimeout()
    }

    @Override
    Long getImplicitlyWait() {
        return getConfig().getImplicitlyWait()
    }

    @Override
    Long getScriptTimeout() {
        return getConfig().getScriptTimeout()
    }

    @Override
    Long getAwaitAtMost() {
        return getConfig().getAwaitAtMost()
    }

    @Override
    Long getAwaitPollingEvery() {
        return getConfig().getAwaitPollingEvery()
    }

    @Override
    Boolean getEventsEnabled() {
        return getConfig().getEventsEnabled()
    }

    @Override
    String getScreenshotPath() {
        return getConfig().getScreenshotPath()
    }

    @Override
    TriggerMode getScreenshotMode() {
        return getConfig().getScreenshotMode()
    }

    @Override
    String getHtmlDumpPath() {
        return getConfig().getHtmlDumpPath()
    }

    @Override
    TriggerMode getHtmlDumpMode() {
        return getConfig().getHtmlDumpMode()
    }

    @Override
    Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return getConfig().getConfigurationDefaults()
    }

    @Override
    Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return getConfig().getConfigurationFactory()
    }

    @Override
    String getCustomProperty(String propertyName) {
        return getConfig().getCustomProperty(propertyName)
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
    <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList)
    }

    @Override
    <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return getFluentControl().newComponentList(listClass, componentClass, componentsList)
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
        return getFluentControl().executeAsyncScript(script, args)
    }

    @Override
    FluentWebElement el(WebElement rawElement) {
        return getFluentControl().el(rawElement)
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

}
