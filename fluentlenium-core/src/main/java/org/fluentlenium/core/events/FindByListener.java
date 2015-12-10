package org.fluentlenium.core.events;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public interface FindByListener {

    void on(final By by, final FluentWebElement element, final WebDriver driver);
}
