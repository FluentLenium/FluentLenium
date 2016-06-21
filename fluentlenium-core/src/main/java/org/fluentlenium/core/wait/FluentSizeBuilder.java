package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static org.fluentlenium.core.wait.WaitMessage.equalToMessage;
import static org.fluentlenium.core.wait.WaitMessage.greatherThanMessage;
import static org.fluentlenium.core.wait.WaitMessage.greatherThanOrEqualToMessage;
import static org.fluentlenium.core.wait.WaitMessage.lessThanMessage;
import static org.fluentlenium.core.wait.WaitMessage.lessThanOrEqualToMessage;
import static org.fluentlenium.core.wait.WaitMessage.notEqualToMessage;

public class FluentSizeBuilder {

    private By locator;
    private FluentWait wait;
    private Search search;
    private List<Filter> filters = new ArrayList<Filter>();

    public FluentSizeBuilder(Search search, FluentWait fluentWait, By locator, List<Filter> filters) {
        this.locator = locator;
        this.wait = fluentWait;
        this.search = search;
        this.filters = filters;
    }

    /**
     * Equals
     *
     * @param size size value
     */
    public void equalTo(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() == size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, equalToMessage(locator, size));
    }

    /**
     * Not equals
     *
     * @param size size value
     */
    public void notEqualTo(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() != size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, notEqualToMessage(locator, size));
    }

    /**
     * Less than
     *
     * @param size size value
     */
    public void lessThan(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() < size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, lessThanMessage(locator, size));
    }

    /**
     * Less than or equals
     *
     * @param size size value
     */
    public void lessThanOrEqualTo(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() <= size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, lessThanOrEqualToMessage(locator, size));
    }

    /**
     * Greater than
     *
     * @param size size value
     */
    public void greaterThan(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() > size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, greatherThanMessage(locator, size));
    }

    /**
     * Greater than or equals
     *
     * @param size size value
     */
    public void greaterThanOrEqualTo(final int size) {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return getSize() >= size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, greatherThanOrEqualToMessage(locator, size));
    }

    private int getSize() {
        if (filters.size() > 0) {
            return search.find(locator, (Filter[]) filters.toArray(new Filter[filters.size()])).size();
        } else {
            return search.find(locator).size();
        }
    }
}
