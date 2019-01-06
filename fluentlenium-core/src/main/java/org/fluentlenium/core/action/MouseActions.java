package org.fluentlenium.core.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;

/**
 * Execute actions with the mouse.
 */
public class MouseActions {
    private final WebDriver driver;

    /**
     * Creates a new mouse actions.
     *
     * @param driver driver
     */
    public MouseActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Get the actions object.
     *
     * @return actions object
     */
    protected org.openqa.selenium.interactions.Actions actions() {
        return new org.openqa.selenium.interactions.Actions(driver);
    }

    /**
     * Basic mouse operations
     *
     * @return low level interface to control the mouse
     * @deprecated Use the following mapping for updating your code:
     * <p>
     * {@link Mouse#click(Coordinates)} to {@link MouseElementActions#click()}
     * <p>
     * {@link Mouse#doubleClick(Coordinates)} to {@link MouseElementActions#doubleClick()}
     * <p>
     * {@link Mouse#mouseDown(Coordinates)} to {@link MouseElementActions#moveToElement()}
     * then {@link MouseElementActions#clickAndHold()}
     * <p>
     * {@link Mouse#mouseUp(Coordinates)} to {@link MouseElementActions#release()}
     * <p>
     * {@link Mouse#mouseMove(Coordinates)} to {@link MouseElementActions#moveToElement()}
     * <p>
     * {@link Mouse#mouseMove(Coordinates, long, long)} to {@link MouseElementActions#moveToElement(int, int)}
     * <p>
     * {@link Mouse#contextClick(Coordinates)} to {@link MouseElementActions#contextClick()}
     */
    @Deprecated
    public Mouse basic() {
        return ((HasInputDevices) driver).getMouse();
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
