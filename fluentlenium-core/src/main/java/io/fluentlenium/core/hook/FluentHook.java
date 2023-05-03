package io.fluentlenium.core.hook;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * Hook interface.
 *
 * @param <T> type of hook options
 */
public interface FluentHook<T> extends WebElement, ElementLocator, WrapsElement, Locatable {
    /**
     * Get the options of the hook.
     *
     * @return hook options
     */
    T getOptions();
}
