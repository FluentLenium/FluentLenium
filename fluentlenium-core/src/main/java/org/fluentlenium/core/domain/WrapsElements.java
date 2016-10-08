package org.fluentlenium.core.domain;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Allows to retrieve underlying wrapped elements.
 */
public interface WrapsElements {
    /**
     * Get the wrapped elements
     *
     * @return wrapped elements
     */
    List<WebElement> getWrappedElements();
}
