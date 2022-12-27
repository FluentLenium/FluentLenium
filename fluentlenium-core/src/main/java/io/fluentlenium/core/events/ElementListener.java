package io.fluentlenium.core.events;

import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebDriver;

/**
 * Listen to element events.
 */
public interface ElementListener {

    /**
     * Called when something occurs on an element.
     *
     * @param element the element
     * @param driver  the selenium driver
     */
    void on(FluentWebElement element, WebDriver driver); // NOPMD ShortMethodName
}
