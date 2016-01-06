package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FillSelectConstructor extends org.fluentlenium.core.Fluent {
    private FluentWebElement element;
    private Filter[] filters;

    public FillSelectConstructor(WebDriver webDriver, Filter... filters) {
        this("*", webDriver, filters);
    }

    public FillSelectConstructor(String cssSelector, WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.element = findFirst(cssSelector, filters);
        this.filters = filters;
    }

    public FillSelectConstructor(FluentWebElement element, WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.element = element;
        this.filters = filters;
    }

    /**
     * Select all options that have a value matching the argument for the Select element.
     *
     * @param value the select matching string
     * @return fill select constructor
     */
    public FillSelectConstructor withValue(String value) {
        Select select = new Select(element.getElement());
        select.selectByValue(value);
        return this;
    }

    /**
     * Select the option by its index for the Select element.
     *
     * @param index the select index value
     * @return fill select constructor
     */
    public FillSelectConstructor withIndex(int index) {
        Select select = new Select(element.getElement());
        select.selectByIndex(index);
        return this;
    }

    /**
     * Select all options that display text matching the argument for the Select element.
     *
     * @param text the select string part
     * @return fill select constructor
     */
    public FillSelectConstructor withText(String text) {
        Select select = new Select(element.getElement());
        select.selectByVisibleText(text);
        return this;
    }
}
