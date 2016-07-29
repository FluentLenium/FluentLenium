package org.fluentlenium.core.action;

import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AbstractFill extends Fluent {
    private String cssSelector;
    private Filter[] filters;
    private By bySelector;
    private FluentList<FluentWebElement> fluentList;

    public AbstractFill(String cssSelector, WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = cssSelector;
        this.filters = filters;
    }

    public AbstractFill(By bySelector, WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.bySelector = bySelector;
        this.filters = filters;
    }

    public AbstractFill(WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = "*";
        this.filters = filters;
    }

    public AbstractFill(FluentList<FluentWebElement> list, WebDriver driver, Filter... filters) {
        super(driver);
        this.filters = filters.clone();
        this.fluentList = list;
    }

    public AbstractFill(FluentWebElement element, WebDriver driver, Filter... filters) {
        this(element.asList(), driver, filters);
    }

    protected FluentList<FluentWebElement> findElements() {
        if (fluentList != null) {
            return fluentList;
        } else if (cssSelector != null) {
            return find(cssSelector, filters);
        } else {
            return find(bySelector, filters);
        }
    }
}
