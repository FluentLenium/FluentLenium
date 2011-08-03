package fr.javafreelance.fluentlenium.domain;

import fr.javafreelance.fluentlenium.action.FluentDefaultActions;
import fr.javafreelance.fluentlenium.filter.Filter;
import fr.javafreelance.fluentlenium.search.Search;
import fr.javafreelance.fluentlenium.search.SearchActions;
import org.openqa.selenium.WebElement;

/**
 * WebElementCustom include a Selenium WebElement. It provides a lot of shortcuts to make selenium more fluent
 */
public class FluentWebElement implements FluentDefaultActions, SearchActions {
    private final WebElement webElement;
    private final Search search;

    public FluentWebElement(WebElement webElement) {
        this.webElement = webElement;
        this.search = new Search(webElement);
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

    /**
     * find elements into the childs with the corresponding filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList find(String name, Filter... filters) {
        return search.find(name, filters);
    }

    /**
     * find elements into the childs with the corresponding filters at the given positiokn
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement find(String name, Integer number, Filter... filters) {
        return search.find(name, number, filters);
    }

    /**
     * find elements into the childs with the corresponding filters at the first position
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement findFirst(String name, Filter... filters) {
        return search.findFirst(name, filters);
    }
}
