package org.fluentlenium.core.events;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

class EventAdapter implements WebDriverEventListener {

    private final EventListener listener;

    public EventAdapter(final EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeNavigateTo(final String url, final WebDriver driver) {
        this.listener.beforeNavigateTo(url, driver);
    }

    @Override
    public void afterNavigateTo(final String url, final WebDriver driver) {
        this.listener.afterNavigateTo(url, driver);
    }

    @Override
    public void beforeNavigateBack(final WebDriver driver) {
        this.listener.beforeNavigateBack(driver);
    }

    @Override
    public void afterNavigateBack(final WebDriver driver) {
        this.listener.afterNavigateBack(driver);
    }

    @Override
    public void beforeNavigateForward(final WebDriver driver) {
        this.listener.beforeNavigateForward(driver);
    }

    @Override
    public void afterNavigateForward(final WebDriver driver) {
        this.listener.afterNavigateForward(driver);
    }

    @Override
    public void beforeFindBy(final By by, final WebElement element, final WebDriver driver) {
        this.listener.beforeFindBy(by, element == null ? null : new FluentWebElement(element),
                driver);
    }

    @Override
    public void afterFindBy(final By by, final WebElement element, final WebDriver driver) {
        this.listener.afterFindBy(by, element == null ? null : new FluentWebElement(element),
                driver);
    }

    @Override
    public void beforeClickOn(final WebElement element, final WebDriver driver) {
        this.listener.beforeClickOn(element == null ? null : new FluentWebElement(element), driver);
    }

    @Override
    public void afterClickOn(final WebElement element, final WebDriver driver) {
        this.listener.afterClickOn(element == null ? null : new FluentWebElement(element), driver);
    }

    @Override
    public void beforeChangeValueOf(final WebElement element, final WebDriver driver) {
        this.listener.beforeChangeValueOf(element == null ? null : new FluentWebElement(element),
                driver);
    }

    @Override
    public void afterChangeValueOf(final WebElement element, final WebDriver driver) {
        this.listener.afterChangeValueOf(element == null ? null : new FluentWebElement(element),
                driver);
    }

    @Override
    public void beforeScript(final String script, final WebDriver driver) {
        this.listener.beforeScript(script, driver);
    }

    @Override
    public void afterScript(final String script, final WebDriver driver) {
        this.listener.afterScript(script, driver);
    }

    @Override
    public void onException(final Throwable throwable, final WebDriver driver) {
        this.listener.onException(throwable, driver);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        EventAdapter that = (EventAdapter) o;

        return !(this.listener != null ? !this.listener.equals(that.listener)
                : that.listener != null);

    }

    @Override
    public int hashCode() {
        return this.listener != null ? this.listener.hashCode() : 0;
    }
}
