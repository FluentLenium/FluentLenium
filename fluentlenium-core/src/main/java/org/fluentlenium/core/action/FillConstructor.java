package org.fluentlenium.core.action;

import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.WebDriver;

public class FillConstructor extends org.fluentlenium.core.Fluent {
    private String cssSelector;
    private Filter[] filters;
    private FluentDefaultActions fluentList;

    public FillConstructor(String cssSelector, WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = cssSelector;
        this.filters = filters;
    }
    
    public FillConstructor(WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = "*";
        this.filters = filters;
    }

    public FillConstructor(FluentDefaultActions list, WebDriver driver, Filter... filters) {
        super(driver);
        this.filters = filters.clone();
        this.fluentList = list;
    }

    /**
     * Set the values params as text for the fluentList or search a new list with the css selector and filters and add the values param on it
     *
     * @param values value to search
     * @return fill constructor
     */
    public FillConstructor with(String... values) {
        if (fluentList != null) {
            fluentList.text(values);
        } else {
            $(cssSelector, filters).text(values);
        }
        return this;
    }

    public FillConstructor withValues(String... values) {
        // with is a reserved word in Scala...
        return with(values);
    }
}
