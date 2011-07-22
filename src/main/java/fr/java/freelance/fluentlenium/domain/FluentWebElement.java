package fr.java.freelance.fluentlenium.domain;

import org.openqa.selenium.WebElement;

/**
 * WebElementCustom include a Selenium WebElement. It provides a lot of shortcuts to make selenium more fluent
 */
public class FluentWebElement implements FluentDefaultActions {
    private final WebElement webElement;

    public FluentWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    /**
     * Click on the element
     */
    public void click() {
        webElement.click();
    }

    /**
     * Clear the element
     */
    public void clear() {
        webElement.clear();
    }

    /**
     * Submit the element
     */
    public void submit() {
        webElement.submit();
    }

    public void text(String... text) {
        webElement.clear();
        if (text.length != 0) {
            webElement.sendKeys(text[0]);
        }
    }

    public String getName() {
        return webElement.getAttribute("name");
    }

    public String getAttrbibute(String attribute) {
        return webElement.getAttribute(attribute);
    }

    public String getId() {
        return webElement.getAttribute("id");
    }

    public String getText() {
        return webElement.getText();
    }

    public String getValue() {
        return webElement.getAttribute("value");
    }

    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    public boolean isSelected() {
        return webElement.isSelected();
    }

    public String getTagName() {
        return webElement.getTagName();
    }
}
