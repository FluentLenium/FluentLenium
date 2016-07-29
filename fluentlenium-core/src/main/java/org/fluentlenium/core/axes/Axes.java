package org.fluentlenium.core.axes;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles XPath axes for an element (http://www.w3schools.com/xsl/xpath_axes.asp)
 */
public class Axes {

    private final WebDriver driver;
    private final WebElement webElement;


    public Axes(WebDriver driver, WebElement element) {
        this.driver = driver;
        this.webElement = element;
    }


    /**
     * Find parent element.
     *
     * @return fluent web element
     */
    public FluentWebElement parent() {
        WebElement parentRaw = this.webElement.findElement(By.xpath("parent::*"));
        FluentWebElement parent = new FluentWebElement(parentRaw, driver);
        return parent;
    }

    protected FluentList<FluentWebElement> handleAxe(String axe) {
        List<WebElement> ancestorsRaw = this.webElement.findElements(By.xpath(axe + "::*"));
        List<FluentWebElement> elements = new ArrayList<FluentWebElement>();
        for (WebElement ancestor : ancestorsRaw) {
            elements.add(new FluentWebElement(ancestor, driver));
        }
        return new FluentListImpl<>(elements);
    }

    /**
     * Find ancestor elements.
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> ancestors() {
        return handleAxe("ancestor");
    }

    /**
     * Find descendants elements (children, grandchildren, etc.).
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> descendants() {
        return handleAxe("descendant");
    }

    /**
     * Find following elements.
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> followings() {
        return handleAxe("following");
    }

    /**
     * Find following sibling elements.
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> followingSiblings() {
        return handleAxe("following-sibling");
    }

    /**
     * Find preceding elements. (Ancestors are NOT included)
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> precedings() {
        return handleAxe("preceding");
    }

    /**
     * Find preceding sibling elements.
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> precedingSiblings() {
        return handleAxe("preceding-sibling");
    }


}
