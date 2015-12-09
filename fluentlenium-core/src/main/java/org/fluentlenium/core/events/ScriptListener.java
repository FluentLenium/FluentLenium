package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;

public interface ScriptListener {

    void on(final String script, final WebDriver driver);
}
