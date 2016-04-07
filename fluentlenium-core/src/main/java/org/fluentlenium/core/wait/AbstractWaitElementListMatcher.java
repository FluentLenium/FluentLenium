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

/**
 * Base Matcher for waiting on element list.
 */
public abstract class AbstractWaitElementListMatcher extends AbstractWaitElementMatcher {
    public AbstractWaitElementListMatcher(Search search, FluentWait wait, String selectionName) {
        super(search, wait, selectionName);
    }


    /**
     * Check that the elements are all enabled
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
     * Check that all the elements are clickable
     */
    public void areClickable() {
        Predicate<Fluent> isClickable = new com.google.common.base.Predicate<Fluent>() {

            @Override
            public boolean apply(Fluent input) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                if (fluentWebElements.size() > 0) {
                    for (FluentWebElement element : find()) {
                        if (!element.conditions().isClickable()) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        };

        until(wait, isClickable, isClickableMessage(selectionName));
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
