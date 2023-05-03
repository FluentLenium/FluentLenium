package io.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

/**
 * Listen to switch window
 */
public interface SwitchToWindowListener {
    /**
     * Called before or after window switch event.
     *
     * @param s      script
     * @param driver selenium driver
     */
    void on(String s, WebDriver driver); // NOPMD ShortMethodName
}
