package io.fluentlenium.core.events;

import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Listener interface for FindBy events
 */
public interface FindByListener {

    /**
     * Invoked when an element has been found.
     *
     * @param by      locator that found the element
     * @param element element found
     * @param driver  selenium webdriver
     */
    void on(By by, FluentWebElement element, WebDriver driver); // NOPMD ShortMethodName
}
