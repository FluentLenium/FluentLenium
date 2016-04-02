package org.fluentlenium.core.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class MouseActions {

    private final WebDriver webDriver;

    public MouseActions(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Action mouseOver(WebElement webElement) {
        return new Actions(webDriver).moveToElement(webElement).build();
    }

    public Action doubleClick(WebElement webElement) {
        return new Actions(webDriver).doubleClick(webElement).build();
    }
}
