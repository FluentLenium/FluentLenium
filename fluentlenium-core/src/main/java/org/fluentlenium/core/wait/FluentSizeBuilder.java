package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;

import java.util.ArrayList;
import java.util.List;

import static org.fluentlenium.core.wait.WaitMessage.*;

public class FluentSizeBuilder {

    private String selector;
    private FluentWait wait;
    private Search search;
    private List<Filter> filters = new ArrayList<Filter>();

    public FluentSizeBuilder(Search search, FluentWait fluentWait, String selector, List<Filter> filters) {
        this.selector = selector;
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
        FluentWaitMatcher.until(wait, isPresent, filters, equalToMessage(selector, size));
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
        FluentWaitMatcher.until(wait, isPresent, filters, notEqualToMessage(selector, size));
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
        FluentWaitMatcher.until(wait, isPresent, filters, lessThanMessage(selector, size));
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
        FluentWaitMatcher.until(wait, isPresent, filters, lessThanOrEqualToMessage(selector, size));
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
        FluentWaitMatcher.until(wait, isPresent, filters, greatherThanMessage(selector, size));
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
        FluentWaitMatcher.until(wait, isPresent, filters, greatherThanOrEqualToMessage(selector, size));
    }

    private int getSize() {
        if (filters.size() > 0) {
            return search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()])).size();
        } else {
            return search.find(selector).size();
        }
    }


}
