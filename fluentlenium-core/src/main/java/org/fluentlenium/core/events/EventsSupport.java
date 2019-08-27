package org.fluentlenium.core.events;

import static java.util.Objects.requireNonNull;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

/**
 * EventsSupport can be registered in Selenium {@link org.openqa.selenium.support.events.EventFiringWebDriver} to provide an
 * easier to use events interface.
 */
@SuppressWarnings({"PMD.GodClass"})
public class EventsSupport implements EventListener {

    private final EventsRegistry eventsRegistry;

    /**
     * Creates a new events support
     *
     * @param eventsRegistry events registry
     */
    public EventsSupport(EventsRegistry eventsRegistry) {
        this.eventsRegistry = requireNonNull(eventsRegistry);
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        eventsRegistry.beforeNavigateTo.forEach(listener -> listener.on(url, driver));
        eventsRegistry.beforeNavigate.forEach(listener -> listener.on(url, driver, null));
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        eventsRegistry.afterNavigateTo.forEach(listener -> listener.on(url, driver));
        eventsRegistry.afterNavigate.forEach(listener -> listener.on(url, driver, null));
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        eventsRegistry.beforeNavigateBack.forEach(listener -> listener.on(driver));
        eventsRegistry.beforeNavigate.forEach(listener -> listener.on(null, driver, NavigateAllListener.Direction.BACK));
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        eventsRegistry.afterNavigateBack.forEach(listener -> listener.on(driver));
        eventsRegistry.afterNavigate.forEach(listener -> listener.on(null, driver, NavigateAllListener.Direction.BACK));
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        eventsRegistry.beforeNavigateForward.forEach(listener -> listener.on(driver));
        eventsRegistry.beforeNavigate.forEach(listener -> listener.on(null, driver, NavigateAllListener.Direction.FORWARD));
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        eventsRegistry.afterNavigateForward.forEach(listener -> listener.on(driver));
        eventsRegistry.afterNavigate.forEach(listener -> listener.on(null, driver, NavigateAllListener.Direction.FORWARD));
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        eventsRegistry.beforeNavigateRefresh.forEach(listener -> listener.on(driver));
        eventsRegistry.beforeNavigate.forEach(listener -> listener.on(null, driver, NavigateAllListener.Direction.REFRESH));
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        eventsRegistry.afterNavigateRefresh.forEach(listener -> listener.on(driver));
        eventsRegistry.afterNavigate.forEach(listener -> listener.on(null, driver, NavigateAllListener.Direction.REFRESH));
    }

    @Override
    public void beforeFindBy(By by, FluentWebElement element, WebDriver driver) {
        eventsRegistry.beforeFindBy.forEach(listener -> listener.on(by, element, driver));
    }

    @Override
    public void afterFindBy(By by, FluentWebElement element, WebDriver driver) {
        eventsRegistry.afterFindBy.forEach(listener -> listener.on(by, element, driver));
    }

    @Override
    public void beforeClickOn(FluentWebElement element, WebDriver driver) {
        eventsRegistry.beforeClickOn.forEach(listener -> listener.on(element, driver));
    }

    @Override
    public void afterClickOn(FluentWebElement element, WebDriver driver) {
        eventsRegistry.afterClickOn.forEach(listener -> listener.on(element, driver));
    }

    @Override
    public void beforeChangeValueOf(FluentWebElement element, WebDriver driver, CharSequence[] charSequence) {
        eventsRegistry.beforeChangeValueOf.forEach(listener -> listener.on(element, driver));
    }

    @Override
    public void afterChangeValueOf(FluentWebElement element, WebDriver driver, CharSequence[] charSequence) {
        eventsRegistry.afterChangeValueOf.forEach(listener -> listener.on(element, driver));
    }

    @Override
    public void beforeGetText(FluentWebElement webElement, WebDriver webDriver) {
        eventsRegistry.beforeGetText.forEach(listener -> listener.on(webElement, webDriver));
    }

    @Override
    public void afterGetText(FluentWebElement webElement, WebDriver webDriver, String s) {
        eventsRegistry.afterGetText.forEach(listener -> listener.on(webElement, webDriver));
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        eventsRegistry.beforeScript.forEach(listener -> listener.on(script, driver));
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        eventsRegistry.afterScript.forEach(listener -> listener.on(script, driver));
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        eventsRegistry.onException.forEach(listener -> listener.on(throwable, driver));
    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        eventsRegistry.beforeAlertAccept.forEach(listener -> listener.on(driver));
    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
        eventsRegistry.afterAlertAccept.forEach(listener -> listener.on(driver));
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        eventsRegistry.beforeAlertDismiss.forEach(listener -> listener.on(driver));
    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
        eventsRegistry.afterAlertDismiss.forEach(listener -> listener.on(driver));
    }

    @Override
    public void beforeSwitchToWindow(String s, WebDriver webDriver) {
        eventsRegistry.beforeSwitchToWindow.forEach(listener -> listener.on(s, webDriver));
    }

    @Override
    public void afterSwitchToWindow(String s, WebDriver webDriver) {
        eventsRegistry.afterSwitchToWindow.forEach(listener -> listener.on(s, webDriver));
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {
        eventsRegistry.beforeGetScreenshotAs.forEach(listener -> ((GetScreenshotAsListener<X>) listener).on(outputType));
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {
        eventsRegistry.beforeGetScreenshotAs.forEach(listener -> ((GetScreenshotAsListener<X>) listener).on(outputType, x));
    }
}
