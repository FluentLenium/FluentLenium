package io.fluentlenium.core.action;

import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Execute actions with the keyboard on a defined element. Triggers element search before performing an action.
 */
public class KeyboardElementActions {
    private final WebDriver driver;
    private final WebElement element;

    /**
     * Creates a new object to execute actions with the keyboard, using given selenium driver and element.
     *
     * @param driver  selenium driver
     * @param element element on which to execute actions
     */
    public KeyboardElementActions(WebDriver driver, WebElement element) {
        this.driver = driver;
        this.element = element;
    }

    /**
     * Creates a new object to execute actions with the keyboard, using given selenium driver and element.
     *
     * @param driver           selenium driver
     * @param fluentWebElement FluentWebElement on which to execute actions
     */
    public KeyboardElementActions(WebDriver driver, FluentWebElement fluentWebElement) {
        this(driver, fluentWebElement.getElement());
    }

    /**
     * Get selenium interactions actions.
     *
     * @return selenium actions
     */
    private Actions actions() {
        return new Actions(driver);
    }

    /**
     * Performs a modifier key press after focusing on an element. Equivalent to:
     * <i>Actions.click(element).sendKeys(theKey);</i>
     *
     * @param theKey Either {@link Keys#SHIFT}, {@link Keys#ALT} or {@link Keys#CONTROL}. If the
     *               provided key is none of those, {@link IllegalArgumentException} is thrown.
     * @return this object reference to chain calls
     * @see #keyDown(org.openqa.selenium.Keys)
     * @see Actions#keyDown(WebElement, CharSequence)
     */
    public KeyboardElementActions keyDown(Keys theKey) {
        loadElementAndPerform(actions().keyDown(element, theKey));
        return this;
    }

    /**
     * Performs a modifier key release after focusing on an element. Equivalent to:
     * <i>Actions.click(element).sendKeys(theKey);</i>
     *
     * @param theKey Either {@link Keys#SHIFT}, {@link Keys#ALT} or {@link Keys#CONTROL}.
     * @return this object reference to chain calls
     * @see Actions#keyUp(WebElement, CharSequence)
     */
    public KeyboardElementActions keyUp(Keys theKey) {
        loadElementAndPerform(actions().keyUp(element, theKey));
        return this;
    }

    /**
     * Sends keys to the active element. This differs from calling
     * {@link WebElement#sendKeys(CharSequence...)} on the active element in two ways:
     * <ul>
     * <li>The modifier keys included in this call are not released.</li>
     * <li>There is no attempt to re-focus the element - so sendKeys(Keys.TAB) for switching
     * elements should work. </li>
     * </ul>
     *
     * @param keysToSend The keys.
     * @return this object reference to chain calls
     * @see Actions#sendKeys(WebElement, CharSequence...)
     */
    public KeyboardElementActions sendKeys(CharSequence... keysToSend) {
        loadElementAndPerform(actions().sendKeys(element, keysToSend));
        return this;
    }

    private void loadElementAndPerform(Actions action) {
        loadElement();
        action.perform();
    }

    private void loadElement() {
        LocatorProxies.now(element);
    }
}
