package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

public interface NavigateToListener {

    void on(final String url, final WebDriver driver);
}
