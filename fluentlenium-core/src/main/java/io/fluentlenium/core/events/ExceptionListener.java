package io.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

/**
 * Listen to exceptions.
 */
public interface ExceptionListener {

    /**
     * Called when an exception is thrown.
     *
     * @param throwable thrown exception
     * @param driver    selenium driver
     */
    void on(Throwable throwable, WebDriver driver); // NOPMD ShortMethodName
}
