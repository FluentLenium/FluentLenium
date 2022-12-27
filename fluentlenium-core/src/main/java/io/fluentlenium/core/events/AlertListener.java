package io.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

/**
 * Listen to alert
 */
public interface AlertListener {
    /**
     * Called when an alert is accepted or dismissed.
     *
     * @param driver    selenium driver
     */
    void on(WebDriver driver); // NOPMD ShortMethodName
}
