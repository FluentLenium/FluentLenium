package io.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

/**
 * Listener interface for Navigate events
 */
public interface NavigateListener {

    /**
     * Invoked when a navigation event occurs.
     *
     * @param driver selenium webdriver
     */
    void on(WebDriver driver); // NOPMD ShortMethodName
}
