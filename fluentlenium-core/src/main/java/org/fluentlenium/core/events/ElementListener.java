package org.fluentlenium.core.events;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebDriver;

public interface ElementListener {

    void on(final FluentWebElement element, final WebDriver driver);
}
