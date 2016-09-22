package org.fluentlenium.core.wait;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.IntegerConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

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

    public boolean verified(final Predicate<FluentWebElement> predicate, final boolean defaultValue) {
        until(wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.verify(predicate, defaultValue);
            }
        });
        return true;
    }

    public IntegerConditions size() {
        return each().hasSize();
    }

    public boolean size(final int size) {
        return each().hasSize(size);
    }

}
