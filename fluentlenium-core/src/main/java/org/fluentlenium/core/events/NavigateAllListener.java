package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

public interface NavigateAllListener {

    enum Direction {
        BACK, FORWARD, REFRESH
    }

    void on(String url, WebDriver driver, Direction direction);
}
