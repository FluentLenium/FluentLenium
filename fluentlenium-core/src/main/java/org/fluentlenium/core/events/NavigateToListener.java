package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

public interface NavigateToListener {

    void on(String url, WebDriver driver);
}
