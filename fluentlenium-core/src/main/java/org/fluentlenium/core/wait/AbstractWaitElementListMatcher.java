package org.fluentlenium.core.wait;


import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.conditions.IntegerConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateNotVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateVerifiedMessage;

/**
 * Base Matcher for waiting on element list.
 */
public abstract class AbstractWaitElementListMatcher extends AbstractWaitElementMatcher {
    protected AbstractWaitElementListMatcher(Search search, FluentWait wait, String selectionName) {
        super(search, wait, selectionName);
    }

    public FluentWaitElementEachMatcher each() {
        return new FluentWaitElementEachMatcher(this);
    }

    public boolean isVerified(final Predicate<FluentWebElement> predicate, final boolean defaultValue) {
        until(wait, new Predicate<FluentDriver>() {
            @Override
            public boolean apply(FluentDriver input) {
                return condition().isVerified(predicate, defaultValue);
            }
        }, negation ? isPredicateNotVerifiedMessage(selectionName) : isPredicateVerifiedMessage(selectionName));
        return true;
    }

    public IntegerConditions hasSize() {
        return each().hasSize();
    }

    public boolean hasSize(final int size) {
        return each().hasSize(size);
    }

}
