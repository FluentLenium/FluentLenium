package org.fluentlenium.core.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

public class MouseActions {
    private WebDriver driver;

    private Keyboard keyboard;
    private Mouse mouse;

    public MouseActions(WebDriver driver) {
        this.driver = driver;
    }

    public MouseActions(Keyboard keyboard, Mouse mouse) {
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
     * Basic mouse operations
     *
     * @return low level interface to control the mouse
     */
    public Mouse basic() {
        if (mouse != null) {
            return mouse;
        } else {
            return ((HasInputDevices) driver).getMouse();
        }
    }

    /**
     * Clicks (without releasing) at the current mouse location.
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#clickAndHold()
     */
    public MouseActions clickAndHold() {
        actions().clickAndHold().perform();
        return this;
    }

    /**
     * Releases the depressed left mouse button at the current mouse location.
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#release()
     */
    public MouseActions release() {
        actions().release().perform();
        return this;
    }

    /**
     * Clicks at the current mouse location. Useful when combined with
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#click()
     */
    public MouseActions click() {
        actions().click().perform();
        return this;
    }

    /**
     * Performs a double-click at the current mouse location.
     *
     * @return this object reference to chain calls
     */
    public MouseActions doubleClick() {
        actions().doubleClick().perform();
        return this;
    }

    /**
     * Performs a context-click at the current mouse location.
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#contextClick()
     */
    public MouseActions contextClick() {
        actions().contextClick().perform();
        return this;
    }
}
