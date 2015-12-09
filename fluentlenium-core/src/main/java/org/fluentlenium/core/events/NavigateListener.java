package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

public interface NavigateListener {

    void on(final WebDriver driver);
}
