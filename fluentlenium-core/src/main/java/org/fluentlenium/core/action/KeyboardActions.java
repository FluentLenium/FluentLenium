package org.fluentlenium.core.action;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

/**
 * Execute actions with the keyboard.
 */
public class KeyboardActions {
    private WebDriver driver;
    private Keyboard keyboard;
    private Mouse mouse;

    /**
     * Creates a new object to execute actions with the keyboard, using given selenium driver.
     *
     * @param driver selenium driver
     */
    public KeyboardActions(final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Creates a new object to execute actions with the keyboard, using given selenium Keyboard and Mouse interfaces.
     *
     * @param keyboard keyboard interface
     * @param mouse    mouse interface
     */
    public KeyboardActions(final Keyboard keyboard, final Mouse mouse) {
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    /**
     * Get selenium interactions actions.
     *
     * @return selenium actions
     */
    protected org.openqa.selenium.interactions.Actions actions() {
        if (driver == null) {
            return new org.openqa.selenium.interactions.Actions(keyboard, mouse);
        }
        return new org.openqa.selenium.interactions.Actions(driver);
    }

    /**
     * Basic keyboard operations
     *
     * @return low level interface to control the keyboard
     */
    public Keyboard basic() {
        if (keyboard == null) {
            return ((HasInputDevices) driver).getKeyboard();
        }
        return keyboard;
    }

    /**
     * Performs a modifier key press. Does not release the modifier key - subsequent interactions
     * may assume it's kept pressed.
     * Note that the modifier key is <b>never</b> released implicitly - either
     * <i>keyUp(theKey)</i> or <i>sendKeys(Keys.NULL)</i>
     * must be called to release the modifier.
     *
     * @param theKey Either {@link Keys#SHIFT}, {@link Keys#ALT} or {@link Keys#CONTROL}. If the
     *               provided key is none of those, {@link IllegalArgumentException} is thrown.
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#keyDown(Keys)
     */
    public KeyboardActions keyDown(final Keys theKey) {
        actions().keyDown(theKey).perform();
        return this;
    }

    /**
     * Performs a modifier key release. Releasing a non-depressed modifier key will yield undefined
     * behaviour.
     *
     * @param theKey Either {@link Keys#SHIFT}, {@link Keys#ALT} or {@link Keys#CONTROL}.
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#keyUp(Keys)
     */
    public KeyboardActions keyUp(final Keys theKey) {
        actions().keyUp(theKey).perform();
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
     * @return A self reference.
     * @see org.openqa.selenium.interactions.Actions#sendKeys(CharSequence...)
     */
    public KeyboardActions sendKeys(final CharSequence... keysToSend) {
        actions().sendKeys(keysToSend).perform();
        return this;
    }
}
