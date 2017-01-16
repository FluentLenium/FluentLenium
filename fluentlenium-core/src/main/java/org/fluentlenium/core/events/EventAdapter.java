package org.fluentlenium.core.events;

import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.Objects;

/**
 * FluentLenium adapter for Selenium events listener.
 */
class EventAdapter implements WebDriverEventListener {

    private final EventListener listener;
    private final ComponentInstantiator instantiator;

    /**
     * Creates a new event adapter.
     *
     * @param listener     underlysing listener
     * @param instantiator component instantiator
     */
    EventAdapter(EventListener listener, ComponentInstantiator instantiator) {
        this.listener = listener;
        this.instantiator = instantiator;
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        listener.beforeNavigateTo(url, driver);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        listener.afterNavigateTo(url, driver);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        listener.beforeNavigateBack(driver);
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        listener.afterNavigateBack(driver);
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        listener.beforeNavigateForward(driver);
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        listener.afterNavigateForward(driver);
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        listener.beforeNavigateRefresh(driver);
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        listener.afterNavigateRefresh(driver);
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        listener.beforeFindBy(by, element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        listener.afterFindBy(by, element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        listener.beforeClickOn(element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        listener.afterClickOn(element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        listener.beforeChangeValueOf(element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {
        listener.afterChangeValueOf(element == null ? null : instantiator.newComponent(FluentWebElement.class, element), driver);
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        listener.beforeScript(script, driver);
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        listener.afterScript(script, driver);
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        listener.onException(throwable, driver);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EventAdapter that = (EventAdapter) obj;
        return Objects.equals(listener, that.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listener);
    }
}
