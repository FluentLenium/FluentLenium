package org.fluentlenium.core.events;

import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.Objects;

class EventAdapter implements WebDriverEventListener {

    private final EventListener listener;
    private final ComponentInstantiator instantiator;

    EventAdapter(final EventListener listener, final ComponentInstantiator instantiator) {
        this.listener = listener;
        this.instantiator = instantiator;
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
    public void beforeNavigateRefresh(final WebDriver driver) {
        this.listener.beforeNavigateRefresh(driver);
    }

    @Override
    public void afterNavigateRefresh(final WebDriver driver) {
        this.listener.afterNavigateRefresh(driver);
    }

    @Override
    public void beforeFindBy(final By by, final WebElement element, final WebDriver driver) {
        this.listener
                .beforeFindBy(by, element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void afterFindBy(final By by, final WebElement element, final WebDriver driver) {
        this.listener
                .afterFindBy(by, element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void beforeClickOn(final WebElement element, final WebDriver driver) {
        this.listener.beforeClickOn(element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void afterClickOn(final WebElement element, final WebDriver driver) {
        this.listener.afterClickOn(element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] charSequences) {
        this.listener
                .beforeChangeValueOf(element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] charSequences) {
        this.listener
                .afterChangeValueOf(element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventAdapter that = (EventAdapter) o;
        return Objects.equals(listener, that.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listener);
    }
}
