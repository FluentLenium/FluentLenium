package org.fluentlenium.core.wait;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.fluentlenium.core.search.Search;

import java.util.ArrayList;
import java.util.List;

public class FluentWaitSelectorMatcher extends AbstractWaitElementListMatcher {
    private String selector;
    private List<Filter> filters = new ArrayList<Filter>();

    static final String SELECTOR = "Selector";

    public FluentWaitSelectorMatcher(Search search, FluentWait fluentWait, String selector) {
        super(search, fluentWait, SELECTOR + " " + selector);
        this.selector = selector;
    }

    /**
     * Create a filter builder for the attribute
     *
     * @param attribute attribute name
     * @return fluent wait builder
     */
    public FluentWaitFiltersBuilder with(String attribute) {
        return new FluentWaitFiltersBuilder(this, attribute);
    }

    void addFilter(Filter filter) {
        this.filters.add(filter);
    }

    protected String buildMessage(String defaultMessage) {
        StringBuilder message = new StringBuilder(defaultMessage);
        if (filters != null && !filters.isEmpty()) {
            for (Filter filter : filters) {
                message.append(filter.toString());
            }
            message.append(" Filters : ");
        }
        return message.toString();
    }

    protected FluentList<FluentWebElement> find() {
        if (filters.size() > 0) {
            return findWithFilter();
        } else {
            return search.find(selector);
        }
    }

    private FluentList<FluentWebElement> findWithFilter() {
        return search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()]));
    }

    /**
     * Create a filter builder for the attribute by id
     *
     * @return fluent wait builder
     */
    public FluentWaitFiltersBuilder withId() {
        return new FluentWaitFiltersBuilder(this, FilterType.ID);
    }

    /**
     * Check that the element has the corrsponding id
     *
     * @param value id name
     * @return fluent wait builder
     */
    public AbstractWaitElementMatcher withId(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withId(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by name
     *
     * @return fluent wait builder
     */
    public FluentWaitFiltersBuilder withName() {
        return new FluentWaitFiltersBuilder(this, FilterType.NAME);
    }

    /**
     * Check that the element has the corresponding name
     *
     * @param value element name
     * @return fluent wait builder
     */
    public AbstractWaitElementMatcher withName(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withName(value));
        return this;
    }


    /**
     * Create a filter builder for the attribute by class
     *
     * @return fluent wait builder
     */
    public FluentWaitFiltersBuilder withClass() {
        return new FluentWaitFiltersBuilder(this, FilterType.CLASS);
    }

    /**
     * Check that the element has the corresponding class
     *
     * @param value class name
     * @return fluent wait matcher
     */
    public AbstractWaitElementMatcher withClass(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withClass(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by text
     *
     * @return fluent wait builder
     */
    public FluentWaitFiltersBuilder withText() {
        return new FluentWaitFiltersBuilder(this, FilterType.TEXT);
    }

    /**
     * Check that the element has the corresponding text
     *
     * @param value the text value which should be included
     * @return fluent wait matcher
     */
    public AbstractWaitElementMatcher withText(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withText(value));
        return this;
    }
}
