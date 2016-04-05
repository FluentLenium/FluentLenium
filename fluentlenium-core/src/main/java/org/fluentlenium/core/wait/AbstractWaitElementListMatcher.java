package org.fluentlenium.core.wait;


import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

import static org.fluentlenium.core.wait.WaitMessage.hasSizeMessage;
import static org.fluentlenium.core.wait.WaitMessage.isDisplayedMessage;
import static org.fluentlenium.core.wait.WaitMessage.isEnabledMessage;

/**
 * Base Matcher for waiting on element list.
 */
public abstract class AbstractWaitElementListMatcher extends AbstractWaitElementMatcher {
    public AbstractWaitElementListMatcher(Search search, FluentWait wait, String selectionName) {
        super(search, wait, selectionName);
    }


    /**
     * Check that the elements are all enabled
     *
     * @return fluent
     */
    public void areEnabled() {
        Predicate<Fluent> isEnabled = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                if (fluentWebElements.size() > 0) {
                    for (FluentWebElement fluentWebElement : fluentWebElements) {
                        if (!fluentWebElement.isEnabled()) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        };
        until(wait, isEnabled, isEnabledMessage(selectionName));
    }

    /**
     * Check that all the elements are all displayed
     *
     * @return fluent
     */
    public void areDisplayed() {
        Predicate<Fluent> isVisible = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                if (fluentWebElements.size() > 0) {
                    for (FluentWebElement fluentWebElement : fluentWebElements) {
                        if (!fluentWebElement.isDisplayed()) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        };
        until(wait, isVisible, isDisplayedMessage(selectionName));
    }

    /**
     * Check that all the elements are not displayed
     *
     * @return fluent
     */
    public void areNotDisplayed() {
        Predicate<Fluent> isNotVisible = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                for (FluentWebElement fluentWebElement : fluentWebElements) {
                    if (fluentWebElement.isDisplayed()) {
                        return false;
                    }
                }
                return true;
            }
        };
        until(wait, isNotVisible, isDisplayedMessage(selectionName));
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
     * @return fluent
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
