package io.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

/**
 * Listener interface for Script events
 */
public interface ScriptListener {

    /**
     * Invoked when a script has been executed.
     *
     * @param script script sources
     * @param driver selenium webdriver
     */
    void on(String script, WebDriver driver); // NOPMD ShortMethodName
}
