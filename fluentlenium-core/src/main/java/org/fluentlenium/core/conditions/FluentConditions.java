package org.fluentlenium.core.conditions;

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

    /**
     * Check that this element is visible and enabled such that you can click it.
     *
     * @return true if the element can be clicked, false otherwise.
     */
    public boolean isClickable() {
        FluentAdapter fluent = FluentThread.get();
        return ExpectedConditions.elementToBeClickable(element).apply(fluent.getDriver()) != null;
    }

    /**
     * Check that this element is no longer attached to the DOM.
     *
     * @return false is the element is still attached to the DOM, true otherwise.
     */
    public boolean isStale() {
        FluentAdapter fluent = FluentThread.get();
        return ExpectedConditions.stalenessOf(element).apply(fluent.getDriver());
    }
}
