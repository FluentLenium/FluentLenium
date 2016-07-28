package org.fluentlenium.core.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

public class MouseElementActions {
    private WebDriver driver;
    private Keyboard keyboard;
    private Mouse mouse;
    private final WebElement element;

    public MouseElementActions(WebDriver driver, WebElement element) {
        this.driver = driver;
        this.element = element;
    }

    public MouseElementActions(Keyboard keyboard, Mouse mouse, WebElement element) {
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
     * @see org.openqa.selenium.interactions.Actions#clickAndHold(WebElement)
     */
    public MouseElementActions clickAndHold() {
        actions().clickAndHold(element).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#release(WebElement)
     */
    public MouseElementActions release() {
        actions().release(element).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#click(WebElement)
     */
    public MouseElementActions click() {
        actions().click(element).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#doubleClick(WebElement)
     */
    public MouseElementActions doubleClick() {
        actions().doubleClick(element).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#moveToElement(WebElement)
     */
    public MouseElementActions moveToElement() {
        actions().moveToElement(element).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#moveToElement(WebElement, int, int)
     */
    public MouseElementActions moveToElement(int xOffset, int yOffset) {
        actions().moveToElement(element, xOffset, yOffset).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#contextClick(WebElement)
     */
    public MouseElementActions contextClick() {
        actions().contextClick(element).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#dragAndDrop(WebElement, WebElement)
     */
    public MouseElementActions dragAndDropFrom(WebElement source) {
        actions().dragAndDrop(source, element).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#dragAndDrop(WebElement, WebElement)
     */
    public MouseElementActions dragAndDropTo(WebElement target) {
        actions().dragAndDrop(element, target).perform();
        return this;
    }

    /**
     * @see org.openqa.selenium.interactions.Actions#dragAndDropBy(WebElement, int, int)
     */
    public MouseElementActions dragAndDropBy(int xOffset, int yOffset) {
        actions().dragAndDropBy(element, xOffset, yOffset).perform();
        return this;
    }
}
