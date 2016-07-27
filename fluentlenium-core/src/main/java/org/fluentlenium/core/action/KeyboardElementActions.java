package org.fluentlenium.core.action;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

public class KeyboardElementActions {
    private WebDriver driver;
    private Keyboard keyboard;
    private Mouse mouse;
    private final WebElement element;

    public KeyboardElementActions(WebDriver driver, WebElement element) {
        this.driver = driver;
        this.element = element;
    }

    public KeyboardElementActions(Keyboard keyboard, Mouse mouse, WebElement element) {
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.element = element;
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
     * @see org.openqa.selenium.interactions.Actions#keyDown(WebElement, Keys)
     */
    public KeyboardElementActions keyDown(Keys theKey) {
        actions().keyDown(element, theKey).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#keyUp(WebElement, Keys)
     */
    public KeyboardElementActions keyUp(Keys theKey) {
        actions().keyUp(element, theKey).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#sendKeys(CharSequence...)
     */
    public KeyboardElementActions sendKeys(CharSequence... keysToSend) {
        actions().sendKeys(element, keysToSend).perform();
        return this;
    }
}
