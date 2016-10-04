package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

public interface NavigateListener {

    void on(WebDriver driver);
}
