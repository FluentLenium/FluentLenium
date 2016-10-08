package org.fluentlenium.core.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

/**
 * Execute actions with the mouse.
 */
public class MouseActions {
    private WebDriver driver;

    private Keyboard keyboard;
    private Mouse mouse;

    /**
     * Creates a new mouse actions.
     *
     * @param driver driver
     */
    public MouseActions(final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Creates a new mouse actions.
     *
     * @param keyboard the selenium keyboard interface
     * @param mouse    the selenium mouse interface
     */
    public MouseActions(final Keyboard keyboard, final Mouse mouse) {
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    /**
     * Get the actions object.
     *
     * @return actions object
     */
    protected org.openqa.selenium.interactions.Actions actions() {
        if (driver == null) {
            return new org.openqa.selenium.interactions.Actions(keyboard, mouse);
        }
        return new org.openqa.selenium.interactions.Actions(driver);
    }

    /**
     * Basic mouse operations
     *
     * @return low level interface to control the mouse
     */
    public Mouse basic() {
        if (mouse == null) {
            return ((HasInputDevices) driver).getMouse();
        }
        return mouse;
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
