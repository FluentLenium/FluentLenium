package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

/**
 * Listener interface for Navigate events
 */
public interface NavigateAllListener {

    /**
     * Direction of the navigation
     */
    enum Direction {
        BACK, FORWARD, REFRESH
    }

    /**
     * Invoked when a navigation is performed
     *
     * @param url       destination url
     * @param driver    selenium driver
     * @param direction direction of the navigation
     */
    void on(String url, WebDriver driver, Direction direction);
}
