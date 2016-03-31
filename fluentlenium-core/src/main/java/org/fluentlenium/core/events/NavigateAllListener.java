package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

public interface NavigateAllListener {

    enum Direction {
        BACK, FORWARD, REFRESH
    }

    void on(final String url, final WebDriver driver, final Direction direction);
}
