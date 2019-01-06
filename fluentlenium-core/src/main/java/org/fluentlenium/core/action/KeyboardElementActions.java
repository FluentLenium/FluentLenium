package org.fluentlenium.core.action;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;

/**
 * Execute actions with the keyboard on a defined element.
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
     * Get selenium interactions actions.
     *
     * @return selenium actions
     */
    protected org.openqa.selenium.interactions.Actions actions() {
        return new org.openqa.selenium.interactions.Actions(driver);
    }

    /**
     * Basic keyboard operations
     *
     * @return low level interface to control the keyboard
     * @deprecated Use {@link KeyboardActions#keyDown(Keys)} and {@link KeyboardActions#keyUp(Keys)}
     * and {@link KeyboardActions#sendKeys(CharSequence...)} instead
     */
    @Deprecated
    public Keyboard basic() {
        return ((HasInputDevices) driver).getKeyboard();
    }

    /**
     * Performs a modifier key press after focusing on an element. Equivalent to:
     * <i>Actions.click(element).sendKeys(theKey);</i>
     *
     * @param theKey Either {@link Keys#SHIFT}, {@link Keys#ALT} or {@link Keys#CONTROL}. If the
     *               provided key is none of those, {@link IllegalArgumentException} is thrown.
     * @return this object reference to chain calls
     * @see #keyDown(org.openqa.selenium.Keys)
     * @see org.openqa.selenium.interactions.Actions#keyDown(WebElement, CharSequence)
     */
    public KeyboardElementActions keyDown(Keys theKey) {
        actions().keyDown(element, theKey).perform();
        return this;
    }

    /**
     * Performs a modifier key release after focusing on an element. Equivalent to:
     * <i>Actions.click(element).sendKeys(theKey);</i>
     *
     * @param theKey Either {@link Keys#SHIFT}, {@link Keys#ALT} or {@link Keys#CONTROL}.
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#keyUp(WebElement, CharSequence)
     */
    public KeyboardElementActions keyUp(Keys theKey) {
        actions().keyUp(element, theKey).perform();
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
     * @see org.openqa.selenium.interactions.Actions#sendKeys(WebElement, CharSequence...)
     */
    public KeyboardElementActions sendKeys(CharSequence... keysToSend) {
        actions().sendKeys(element, keysToSend).perform();
        return this;
    }
}
