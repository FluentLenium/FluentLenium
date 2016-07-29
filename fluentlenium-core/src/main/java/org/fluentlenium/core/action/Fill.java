package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Fill extends AbstractFill {
    public Fill(String cssSelector, WebDriver webDriver, Filter... filters) {
        super(cssSelector, webDriver, filters);
    }

    public Fill(By bySelector, WebDriver webDriver, Filter... filters) {
        super(bySelector, webDriver, filters);
    }

    public Fill(WebDriver webDriver, Filter... filters) {
        super(webDriver, filters);
    }

    public Fill(FluentList<FluentWebElement> list, WebDriver driver, Filter... filters) {
        super(list, driver, filters);
    }

    public Fill(FluentWebElement element, WebDriver driver, Filter... filters) {
        super(element, driver, filters);
    }

    /**
     * Set the values params as text for the fluentList or search a new list with the css selector and filters and add the values param on it
     *
     * @param textValues value to search
     * @return fill constructor
     */
    public Fill with(String... textValues) {
        findElements().text(textValues);
        return this;
    }

    public Fill withText
            (String... textValues) {
        return with(textValues);
    }
}
