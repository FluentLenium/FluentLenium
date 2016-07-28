package org.fluentlenium.core.action;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

public class KeyboardActions {
    private WebDriver driver;
    private Keyboard keyboard;
    private Mouse mouse;

    public KeyboardActions(WebDriver driver) {
        this.driver = driver;
    }

    public KeyboardActions(Keyboard keyboard, Mouse mouse) {
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    protected org.openqa.selenium.interactions.Actions actions() {
        if (driver != null) {
            return new org.openqa.selenium.interactions.Actions(driver);
        } else {
            return new org.openqa.selenium.interactions.Actions(keyboard, mouse);
        }
    }

    /**
     * Basic keyboard operations
     *
     * @return low level interface to control the keyboard
     */
    public Keyboard basic() {
        if (keyboard != null) {
            return keyboard;
        } else {
            return ((HasInputDevices) driver).getKeyboard();
        }
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#keyDown(Keys)
     */
    public KeyboardActions keyDown(Keys theKey) {
        actions().keyDown(theKey).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#keyUp(Keys)
     */
    public KeyboardActions keyUp(Keys theKey) {
        actions().keyUp(theKey).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#sendKeys(CharSequence...)
     */
    public KeyboardActions sendKeys(CharSequence... keysToSend) {
        actions().sendKeys(keysToSend).perform();
        return this;
    }
}
