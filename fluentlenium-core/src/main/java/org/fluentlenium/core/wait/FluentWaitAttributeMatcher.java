package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.By;

import java.util.Collections;
import java.util.List;

public class FluentWaitAttributeMatcher<T> {
    private final FluentWait wait;
    private final Search search;
    private final By locator;
    private final List<Filter> filters;
    private final String attributeName;
    private final Function<FluentWebElement, T> attributeSupplier;

    public FluentWaitAttributeMatcher(FluentWait wait, Search search, By locator, List<Filter> filters,
            String attributeName, Function<FluentWebElement, T> attributeSupplier) {
        this.wait = wait;
        this.search = search;
        this.locator = locator;
        this.filters = filters;
        this.attributeName = attributeName;
        this.attributeSupplier = attributeSupplier;
    }

    public Fluent equalTo(final T expectedValue) {
        Predicate<Fluent> equalTo = new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                FluentList<FluentWebElement> elements = search
                        .find(locator, filters.toArray(new Filter[filters.size()]));
                if (elements.isEmpty()) {
                    return false;
                }

                return FluentIterable.from(elements).transform(attributeSupplier)
                        .allMatch(Predicates.equalTo(expectedValue));
            }
        };

        FluentWaitMatcher.until(wait, equalTo, Collections.<Filter>emptyList(),
                WaitMessage.isAttributeEqualToMessage(attributeName, expectedValue));
        return FluentThread.get();
    }
}
