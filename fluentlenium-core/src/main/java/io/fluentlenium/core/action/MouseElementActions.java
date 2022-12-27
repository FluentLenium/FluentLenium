package io.fluentlenium.core.action;

import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;

/**
 * Element specific mouse control interface. Triggers element search before performing an action.
 */
public class MouseElementActions {
    private final WebDriver driver;
    private final WebElement element;

    /**
     * Creates a new mouse element actions.
     *
     * @param driver  selenium driver
     * @param element selenium element
     */
    public MouseElementActions(WebDriver driver, WebElement element) {
        this.driver = driver;
        this.element = element;
    }

    /**
     * Creates a new mouse element actions.
     *
     * @param driver           selenium driver
     * @param fluentWebElement FluentWebElement
     */
    public MouseElementActions(WebDriver driver, FluentWebElement fluentWebElement) {
        this(driver, fluentWebElement.getElement());
    }

    private Actions actions() {
        return new Actions(driver);
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
     * Clicks (without releasing) in the middle of the given element. This is equivalent to:
     * <i>Actions.moveToElement(onElement).clickAndHold()</i>
     *
     * @return this object reference to chain calls
     * @see Actions#clickAndHold(WebElement)
     */
    public MouseElementActions clickAndHold() {
        loadElementAndPerform(actions().clickAndHold(element));
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
     * @see Actions#release(WebElement)
     */
    public MouseElementActions release() {
        loadElementAndPerform(actions().release(element));
        return this;
    }

    /**
     * Clicks in the middle of the given element. Equivalent to:
     * <i>Actions.moveToElement(onElement).click()</i>
     *
     * @return this object reference to chain calls
     * @see Actions#click(WebElement)
     */
    public MouseElementActions click() {
        loadElementAndPerform(actions().click(element));
        return this;
    }

    /**
     * Performs a double-click at middle of the given element. Equivalent to:
     * <i>Actions.moveToElement(element).doubleClick()</i>
     *
     * @return this object reference to chain calls
     * @see Actions#doubleClick(WebElement)
     */
    public MouseElementActions doubleClick() {
        loadElementAndPerform(actions().doubleClick(element));
        return this;
    }

    /**
     * Moves the mouse to the middle of the element. The element is scrolled into view and its
     * location is calculated using getBoundingClientRect.
     *
     * @return this object reference to chain calls
     * @see Actions#moveToElement(WebElement)
     */
    public MouseElementActions moveToElement() {
        loadElementAndPerform(actions().moveToElement(element));
        return this;
    }

    /**
     * Moves the mouse to the middle of the target element. The element is scrolled into view and its
     * location is calculated using getBoundingClientRect.
     *
     * @param target element to move to and release the mouse at.
     * @return this object reference to chain calls
     * @see Actions#moveToElement(WebElement)
     */
    public MouseElementActions moveToElement(WebElement target) {
        loadElementAndPerform(actions().moveToElement(target));
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
     * @see Actions#moveToElement(WebElement, int, int)
     */
    public MouseElementActions moveToElement(int xOffset, int yOffset) {
        loadElementAndPerform(actions().moveToElement(element, xOffset, yOffset));
        return this;
    }

    /**
     * Moves the mouse to an offset from the top-left corner of the target element.
     * The element is scrolled into view and its location is calculated using getBoundingClientRect.
     *
     * @param target  element to move to and release the mouse at.
     * @param xOffset Offset from the top-left corner. A negative value means coordinates left from
     *                the element
     * @param yOffset Offset from the top-left corner. A negative value means coordinates above
     *                the element
     * @return this object reference to chain calls
     * @see Actions#moveToElement(WebElement, int, int)
     */
    public MouseElementActions moveToElement(WebElement target, int xOffset, int yOffset) {
        loadElementAndPerform(actions().moveToElement(target, xOffset, yOffset));
        return this;
    }

    /**
     * Performs a context-click at middle of the given element. First performs a mouseMove
     * to the location of the element.
     *
     * @return this object reference to chain calls
     * @see Actions#contextClick(WebElement)
     */
    public MouseElementActions contextClick() {
        loadElementAndPerform(actions().contextClick(element));
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of the source element,
     * moves to the location of this element (target), then releases the mouse.
     *
     * @param source element to emulate button down at
     * @return this object reference to chain calls
     * @see Actions#dragAndDrop(WebElement, WebElement)
     */
    public MouseElementActions dragAndDropFrom(WebElement source) {
        loadElementAndPerform(actions().dragAndDrop(source, element));
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of this element (source),
     * moves to the location of the target element, then releases the mouse.
     *
     * @param target element to move to and release the mouse at.
     * @return this object reference to chain calls
     * @see Actions#dragAndDrop(WebElement, WebElement)
     */
    public MouseElementActions dragAndDropTo(WebElement target) {
        loadElementAndPerform(actions().dragAndDrop(element, target));
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of this element,
     * moves by a given offset, then releases the mouse.
     *
     * @param xOffset horizontal move offset.
     * @param yOffset vertical move offset.
     * @return this object reference to chain calls
     * @see Actions#dragAndDropBy(WebElement, int, int)
     */
    public MouseElementActions dragAndDropBy(int xOffset, int yOffset) {
        loadElementAndPerform(actions().dragAndDropBy(element, xOffset, yOffset));
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of this element,
     * moves by a given offset of target element, then releases the mouse.
     * <p>
     * This Method is not available in pure Selenium
     *
     * @param target  element to move to and release the mouse at.
     * @param xOffset horizontal move offset.
     * @param yOffset vertical move offset.
     * @return this object reference to chain calls
     * @see Actions#dragAndDropBy(WebElement, int, int)
     */
    public MouseElementActions dragAndDropByWithTargetOffset(WebElement target, int xOffset, int yOffset) {
        loadElementAndPerform(actions().clickAndHold(element).moveToElement(target, xOffset, yOffset).release());
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
