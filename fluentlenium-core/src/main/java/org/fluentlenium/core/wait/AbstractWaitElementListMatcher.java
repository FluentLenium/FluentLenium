package org.fluentlenium.core.wait;


import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

import static org.fluentlenium.core.wait.FluentWaitMessages.hasSizeMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isDisplayedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotDisplayedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isEnabledMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotEnabledMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isClickableMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotClickableMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isSelectedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotSelectedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isStaleMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotStaleMessage;

/**
 * Base Matcher for waiting on element list.
 */
public abstract class AbstractWaitElementListMatcher extends AbstractWaitElementMatcher {
    public AbstractWaitElementListMatcher(Search search, FluentWait wait, String selectionName) {
        super(search, wait, selectionName);
    }

    /**
     * Check that given predicate is verified for all elements.
     *
     * @param predicate    predicate function to verify for each element.
     * @param defaultValue value to use when no element match.
     */
    public void areVerified(Predicate<FluentWebElement> predicate, boolean defaultValue) {
        Predicate<Fluent> allPredicate = buildAllPredicate(predicate, defaultValue);
        until(wait, allPredicate, isPredicateVerifiedMessage(selectionName));
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
     * Check that the elements are all enabled
     */
    public void areEnabled() {
        Predicate<Fluent> predicate = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isEnabled();
            }
        }, false);
        until(wait, predicate, isEnabledMessage(selectionName));
    }

    /**
     * Check that the elements are all not enabled
     */
    public void areNotEnabled() {
        Predicate<Fluent> predicate = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return !input.isEnabled();
            }
        }, false);
        until(wait, predicate, isNotEnabledMessage(selectionName));
    }

    /**
     * Check that all the elements are all displayed
     */
    public void areDisplayed() {
        Predicate<Fluent> predicate = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isDisplayed();
            }
        }, false);
        until(wait, predicate, isDisplayedMessage(selectionName));
    }

    /**
     * Check that all the elements are not displayed
     */
    public void areNotDisplayed() {
        Predicate<Fluent> predicate = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return !input.isDisplayed();
            }
        }, true);
        until(wait, predicate, isNotDisplayedMessage(selectionName));
    }

    /**
     * Check that all the elements are clickable
     */
    public void areClickable() {
        Predicate<Fluent> predicate = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isClickable();
            }
        }, false);

        until(wait, predicate, isClickableMessage(selectionName));
    }

    /**
     * Check that all the elements are not clickable
     */
    public void areNotClickable() {
        Predicate<Fluent> predicate = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return !input.isClickable();
            }
        }, false);

        until(wait, predicate, isNotClickableMessage(selectionName));
    }

    /**
     * Check that all the elements are selected
     */
    public void areSelected() {
        Predicate<Fluent> isSelected = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isSelected();
            }
        }, false);
        until(wait, isSelected, isSelectedMessage(selectionName));
    }

    /**
     * Check that all the elements are not selected
     */
    public void areNotSelected() {
        Predicate<Fluent> isNotSelected = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return !input.isSelected();
            }
        }, false);
        until(wait, isNotSelected, isNotSelectedMessage(selectionName));
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
     * Check that one or more element is not stale
     */
    public void areNotStale() {
        Predicate<Fluent> isClickable = buildAllPredicate(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isStale();
            }
        }, false);
        until(wait, isClickable, isNotStaleMessage(selectionName));
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
