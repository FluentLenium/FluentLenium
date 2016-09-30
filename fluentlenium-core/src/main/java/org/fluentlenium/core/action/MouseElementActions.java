package org.fluentlenium.core.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

/**
 * Element specific mouse control interface.
 */
public class MouseElementActions {
    private WebDriver driver;
    private Keyboard keyboard;
    private Mouse mouse;
    private final WebElement element;

    /**
     * Creates a new mouse element actions.
     *
     * @param driver  selenium driver
     * @param element selenium element
     */
    public MouseElementActions(final WebDriver driver, final WebElement element) {
        this.driver = driver;
        this.element = element;
    }

    /**
     * Creates a new mouse element actions.
     *
     * @param keyboard selenium keyboard interface
     * @param mouse    selenium mouse interface
     * @param element  selenium element
     */
    public MouseElementActions(final Keyboard keyboard, final Mouse mouse, final WebElement element) {
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.element = element;
    }

    private org.openqa.selenium.interactions.Actions actions() {
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
     * Clicks (without releasing) in the middle of the given element. This is equivalent to:
     * <i>Actions.moveToElement(onElement).clickAndHold()</i>
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#clickAndHold(WebElement)
     */
    public MouseElementActions clickAndHold() {
        actions().clickAndHold(element).perform();
        return this;
    }

    /**
     * Releases the depressed left mouse button, in the middle of the given element.
     * This is equivalent to:
     * <i>Actions.moveToElement(onElement).release()</i>
     * <p>
     * Invoking this action without invoking {@link #clickAndHold()} first will result in
     * undefined behaviour.
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#release(WebElement)
     */
    public MouseElementActions release() {
        actions().release(element).perform();
        return this;
    }

    /**
     * Clicks in the middle of the given element. Equivalent to:
     * <i>Actions.moveToElement(onElement).click()</i>
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#click(WebElement)
     */
    public MouseElementActions click() {
        actions().click(element).perform();
        return this;
    }

    /**
     * Performs a double-click at middle of the given element. Equivalent to:
     * <i>Actions.moveToElement(element).doubleClick()</i>
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#doubleClick(WebElement)
     */
    public MouseElementActions doubleClick() {
        actions().doubleClick(element).perform();
        return this;
    }

    /**
     * Moves the mouse to the middle of the element. The element is scrolled into view and its
     * location is calculated using getBoundingClientRect.
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#moveToElement(WebElement)
     */
    public MouseElementActions moveToElement() {
        actions().moveToElement(element).perform();
        return this;
    }

    /**
     * Moves the mouse to an offset from the top-left corner of the element.
     * The element is scrolled into view and its location is calculated using getBoundingClientRect.
     *
     * @param xOffset Offset from the top-left corner. A negative value means coordinates left from
     *                the element
     * @param yOffset Offset from the top-left corner. A negative value means coordinates above
     *                the element
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#moveToElement(WebElement, int, int)
     */
    public MouseElementActions moveToElement(final int xOffset, final int yOffset) {
        actions().moveToElement(element, xOffset, yOffset).perform();
        return this;
    }

    /**
     * Performs a context-click at middle of the given element. First performs a mouseMove
     * to the location of the element.
     *
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#contextClick(WebElement)
     */
    public MouseElementActions contextClick() {
        actions().contextClick(element).perform();
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of the source element,
     * moves to the location of this element (target), then releases the mouse.
     *
     * @param source element to emulate button down at
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#dragAndDrop(WebElement, WebElement)
     */
    public MouseElementActions dragAndDropFrom(final WebElement source) {
        actions().dragAndDrop(source, element).perform();
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of this element (source),
     * moves to the location of the target element, then releases the mouse.
     *
     * @param target element to move to and release the mouse at.
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#dragAndDrop(WebElement, WebElement)
     */
    public MouseElementActions dragAndDropTo(final WebElement target) {
        actions().dragAndDrop(element, target).perform();
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of this element,
     * moves by a given offset, then releases the mouse.
     *
     * @param xOffset horizontal move offset.
     * @param yOffset vertical move offset.
     * @return this object reference to chain calls
     * @see org.openqa.selenium.interactions.Actions#dragAndDropBy(WebElement, int, int)
     */
    public MouseElementActions dragAndDropBy(final int xOffset, final int yOffset) {
        actions().dragAndDropBy(element, xOffset, yOffset).perform();
        return this;
    }
}
