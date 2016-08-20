package org.fluentlenium.core.domain;

import org.fluentlenium.core.components.ComponentException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FailWebElement implements WebElement {
    void fail() {
        throw new ComponentException("A FluentWebElement can't be used after calling as(). You should use " +
                "component instance returned by as() method instead.");
    }

    @Override
    public void click() {
        fail();
    }

    @Override
    public void submit() {
        fail();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        fail();
    }

    @Override
    public void clear() {
        fail();
    }

    @Override
    public String getTagName() {
        fail();
        return null;
    }

    @Override
    public String getAttribute(String name) {
        fail();
        return null;
    }

    @Override
    public boolean isSelected() {
        fail();
        return false;
    }

    @Override
    public boolean isEnabled() {
        fail();
        return false;
    }

    @Override
    public String getText() {
        fail();
        return null;
    }

    @Override
    public List<WebElement> findElements(By by) {
        fail();
        return null;
    }

    @Override
    public WebElement findElement(By by) {
        fail();
        return null;
    }

    @Override
    public boolean isDisplayed() {
        fail();
        return false;
    }

    @Override
    public Point getLocation() {
        fail();
        return null;
    }

    @Override
    public Dimension getSize() {
        fail();
        return null;
    }

    @Override
    public Rectangle getRect() {
        fail();
        return null;
    }

    @Override
    public String getCssValue(String propertyName) {
        fail();
        return null;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        fail();
        return null;
    }
}
