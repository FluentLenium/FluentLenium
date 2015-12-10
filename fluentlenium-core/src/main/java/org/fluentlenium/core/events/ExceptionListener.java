package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

public interface ExceptionListener {

    void on(final Throwable throwable, final WebDriver driver);
}
