package org.fluentlenium.core.wait;


import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

import static org.fluentlenium.core.wait.FluentWaitMessages.hasSizeMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isClickableMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isDisplayedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isEnabledMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isStaleMessage;

/**
 * Base Matcher for waiting on element list.
 */
public abstract class AbstractWaitElementListMatcher extends AbstractWaitElementMatcher {
    public AbstractWaitElementListMatcher(Search search, FluentWait wait, String selectionName) {
        super(search, wait, selectionName);
    }

    /**
     * Check that given predicated is verified for all elements.
     *
     * @param predicate    predicate function to verify for each element.
     * @param defaultValue value to use when no element match.
     */
    public void areVerified(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        Predicate<Fluent> isVerified = buildAllPredicate(predicate, defaultValue);
        until(wait, isVerified, isPredicateVerifiedMessage(selectionName));
    }

    protected Predicate<Fluent> buildAllPredicate(final Predicate<FluentWebElement> predicate, final boolean defaultValue) {
        Predicate<Fluent> untilPredicate = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                if (fluentWebElements.size() > 0) {
                    for (FluentWebElement fluentWebElement : fluentWebElements) {
                        if (!predicate.apply(fluentWebElement)) {
                            return false;
                        }
                    }
                    return true;
                }
                return defaultValue;
            }
        };
        return untilPredicate;
    }


    /**
     * Check that all the elements are enabled
     */
    public void areEnabled() {
        Predicate<Fluent> isEnabled = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isEnabled();
            }
        }, false);
        until(wait, isEnabled, isEnabledMessage(selectionName));
    }

    /**
     * Check that all the elements are displayed
     */
    public void areDisplayed() {
        Predicate<Fluent> isVisible = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isDisplayed();
            }
        }, false);
        until(wait, isVisible, isDisplayedMessage(selectionName));
    }

    /**
     * Check that all the elements are not displayed
     */
    public void areNotDisplayed() {
        Predicate<Fluent> isNotVisible = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return !input.isDisplayed();
            }
        }, true);
        until(wait, isNotVisible, isDisplayedMessage(selectionName));
    }

    /**
     * Check that all the elements are clickable
     */
    public void areClickable() {
        Predicate<Fluent> isClickable = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isClickable();
            }
        }, false);

        until(wait, isClickable, isClickableMessage(selectionName));
    }

    /**
     * Check that one or more element is stale
     */
    public void areStale() {
        Predicate<Fluent> isClickable = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isStale();
            }
        }, false);
        until(wait, isClickable, isStaleMessage(selectionName));
    }

    /**
     * Check that the element have a customized size
     *
     * @return fluent size builder
     */
    public FluentSizeBuilder hasSize() {
        return new FluentSizeBuilder(this, wait, selectionName);
    }

    /**
     * Check that the element have the size indicated
     *
     * @param size integer value of size
     */
    public void hasSize(final int size) {
        Predicate<Fluent> hasSize = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().size() == size;
            }
        };
        until(wait, hasSize, hasSizeMessage(selectionName, size));
    }
}
