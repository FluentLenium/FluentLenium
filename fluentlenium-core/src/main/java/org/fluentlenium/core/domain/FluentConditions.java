package org.fluentlenium.core.domain;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentThread;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Fluent object to handle {@link org.openqa.selenium.support.ui.ExpectedConditions} on FluentWebElement in fluentlenium API.
 */
public class FluentConditions {
    private WebElement element;

    public FluentConditions(WebElement element) {
        this.element = element;
    }

    public boolean isClickable() {
        FluentAdapter fluent = FluentThread.get();
        return ExpectedConditions.elementToBeClickable(element).apply(fluent.getDriver()) != null;
    }
}
