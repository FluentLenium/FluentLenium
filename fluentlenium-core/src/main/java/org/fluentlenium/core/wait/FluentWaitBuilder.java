package org.fluentlenium.core.wait;
import com.google.common.base.Predicate;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.fluentlenium.core.wait.WaitMessage.*;

public class FluentWaitBuilder {
    private List<Filter> filters = new ArrayList<Filter>();
    private Search search;
    private String selector;
    private FluentWait wait;

    public FluentWaitBuilder(Search search, FluentWait fluentWait, String selector) {
        this.selector = selector;
        wait = fluentWait;
        this.search = search;
    }

    /**
     * WARNING - Should be change in a next version to hasAttribute("myAttribute").value("myValue")
     *
     * @param attribute
     * @param value
     */
    public void hasAttribute(final String attribute, final String value) {
        Predicate hasAttribute = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                if (filters.size() > 0) {
                    return search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()])).getAttributes(attribute).contains(value);
                } else {
                    return search.find(selector).getAttributes(attribute).contains(value);
                }
            }
        };
        until(wait, hasAttribute, filters, hasAttributeMessage(selector, attribute, value));
    }

    static void until(FluentWait wait, Predicate present, List<Filter> filters, String defaultMessage) {
        StringBuilder message = new StringBuilder(defaultMessage);
        if (filters != null && !filters.isEmpty()) {
            for (Filter filter : filters) {
                message.append(filter.toString());
            }
            message.append(" Filters : ");
        }
        wait.withMessage(message.toString()).until(present);
    }

    /**
     * check if the FluentWait has the corresponding id
     *
     * @param value
     */
    public void hasId(final String value) {
        Predicate hasId = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                if (filters.size() > 0) {
                    return search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()])).getIds().contains(value);
                } else {
                    return search.find(selector).getIds().contains(value);
                }
            }
        };
        until(wait, hasId, filters, hasIdMessage(selector, value));
    }

    /**
     * check if the FluentWait has the corresponding name
     *
     * @param value
     */
    public void hasName(final String value) {
        Predicate hasName = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                int size1;
                if (filters.size() > 0) {
                    return search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()])).getNames().contains(value);
                } else {
                    return search.find(selector).getNames().contains(value);
                }
            }
        };
        until(wait, hasName, filters, hasNameMessage(selector, value));
    }

    /**
     * Check that the element have a customized size
     *
     * @return
     */
    public FluentSizeBuilder hasSize() {
        return new FluentSizeBuilder(search, wait, selector, filters);
    }

    /**
     * Check that the element have the size indicated
     *
     * @param size
     */
    public void hasSize(final int size) {
        Predicate hasSize = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                int size1;
                if (filters.size() > 0) {
                    size1 = search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()])).size();
                } else {
                    size1 = search.find(selector).size();
                }
                return size1 == size;
            }
        };
        until(wait, hasSize, filters, hasSizeMessage(selector, size));
    }

    /**
     * check if the FluentWait has the corresponding text
     *
     * @param value
     */
    public void hasText(final String value) {
        Predicate hasText = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                if (filters.size() > 0) {
                    return search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()])).getTexts().contains(value);
                } else {
                    return search.find(selector).getTexts().contains(value);
                }
            }
        };
        until(wait, hasText, filters, hasTextMessage(selector, value));
    }

    /**
     * Check that the element is present
     */
    public void isPresent() {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                int size;
                if (filters.size() > 0) {
                    size = search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()])).size();
                } else {
                    size = search.find(selector).size();
                }
                return size > 0;
            }
        };

        until(wait, isPresent, filters, isPresentMessage(selector));
    }

    /**
     * Create a filter builder for the attribute
     *
     * @param attribute
     * @return
     */
    public FluentLeniumWaitBuilder with(String attribute) {
        return new FluentLeniumWaitBuilder(this, attribute);
    }

    /**
     * Create a filter builder for the attribute by id
     *
     * @param
     * @return
     */
    public FluentLeniumWaitBuilder withId() {
        return new FluentLeniumWaitBuilder(this, FilterType.ID);
    }

    /**
     * Check that the element has the corrsponding id
     *
     * @param value
     * @return
     */
    public FluentWaitBuilder withId(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withId(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by name
     *
     * @param
     * @return
     */
    public FluentLeniumWaitBuilder withName() {
        return new FluentLeniumWaitBuilder(this, FilterType.NAME);
    }

    /**
     * Check that the element has the corrsponding name
     *
     * @param value
     * @return
     */
    public FluentWaitBuilder withName(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withName(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by text
     *
     * @param
     * @return
     */
    public FluentLeniumWaitBuilder withText() {
        return new FluentLeniumWaitBuilder(this, FilterType.TEXT);
    }

    /**
     * Check that the element has the corresponding text
     *
     * @param value
     * @return
     */
    public FluentWaitBuilder withText(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withText(value));
        return this;
    }

    void addFilter(Filter filter) {
        this.filters.add(filter);
    }


}
