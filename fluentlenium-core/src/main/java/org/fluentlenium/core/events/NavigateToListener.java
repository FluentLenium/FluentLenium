package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

/**
 * Listener interface for NavigateTo events
 */
public interface NavigateToListener {

    /**
     * Invoked when a navigation event occurs.
     *
     * @param url    url beeing navigated to
     * @param driver selenium webdriver
     */
    void on(String url, WebDriver driver); // NOPMD ShortMethodName
}
