package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.StringConditions;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

import static org.fluentlenium.core.wait.FluentWaitMessages.hasAttributeMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasIdMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNameMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotAttributeMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotIdMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotNameMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isClickableMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isDisplayedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isEnabledMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotClickableMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotDisplayedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotEnabledMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotPresentMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotSelectedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotStaleMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateNotVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPresentMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isSelectedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isStaleMessage;

/**
 * Base Matcher for waiting on a single element.
 */
public abstract class AbstractWaitElementMatcher extends AbstractWaitMatcher implements FluentConditions {
    protected Search search;
    protected String selectionName;
    protected FluentWait wait;
    protected boolean negation = false;

    protected AbstractWaitElementMatcher(Search search, FluentWait wait, String selectionName) {
        this.selectionName = selectionName;
        this.wait = wait;
        this.search = search;
    }

    /**
     * Find the elements from configured matcher.
     *
     * @return fuent list of matching elements.
     */
    abstract protected FluentList<? extends FluentWebElement> find();

    protected FluentListConditions condition() {
        FluentListConditions conditions = find().one();
        if (negation) {
            conditions = conditions.not();
        }
        return conditions;
    }

    @Override
    public boolean isVerified(final Predicate<FluentWebElement> predicate) {
        until(wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return condition().isVerified(predicate);
            }
        }, negation ? isPredicateNotVerifiedMessage(selectionName) : isPredicateVerifiedMessage(selectionName));
        return true;
    }

    @Override
    public boolean hasAttribute(final String attribute, final String value) {
        Predicate<FluentControl> hasAttribute = new com.google.common.base.Predicate<FluentControl>() {
            public boolean apply(FluentControl fluent) {
                return condition().hasAttribute(attribute, value);
            }
        };
        until(wait, hasAttribute, negation ? hasNotAttributeMessage(selectionName, attribute, value) : hasAttributeMessage(selectionName, attribute, value));
        return true;
    }

    @Override
    public boolean hasId(final String value) {
        Predicate<FluentControl> hasId = new com.google.common.base.Predicate<FluentControl>() {
            public boolean apply(FluentControl fluent) {
                return condition().hasId(value);
            }
        };
        until(wait, hasId, negation ? hasNotIdMessage(selectionName, value) : hasIdMessage(selectionName, value));
        return true;
    }

    @Override
    public boolean hasName(final String value) {
        Predicate<FluentControl> hasName = new com.google.common.base.Predicate<FluentControl>() {
            public boolean apply(FluentControl fluent) {
                return condition().hasName(value);
            }
        };
        until(wait, hasName, negation ? hasNotNameMessage(selectionName, value) : hasNameMessage(selectionName, value));
        return true;
    }

    /**
     * Check that one or more element is present
     *
     * @return true if one or more element is present, false otherwise
     */
    public boolean isPresent() {
        Predicate<FluentControl> isPresent = new com.google.common.base.Predicate<FluentControl>() {
            public boolean apply(FluentControl fluent) {
                return condition().isPresent();
            }
        };
        until(wait, isPresent, negation ? isNotPresentMessage(selectionName) : isPresentMessage(selectionName));
        return true;
    }

    @Override
    public boolean isDisplayed() {
        Predicate<FluentControl> isDisplayed = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return condition().isDisplayed();
            }
        };
        until(wait, isDisplayed, negation ? isNotDisplayedMessage(selectionName) : isDisplayedMessage(selectionName));
        return true;
    }

    @Override
    public boolean isEnabled() {
        Predicate<FluentControl> isEnabled = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return condition().isEnabled();
            }
        };
        until(wait, isEnabled, negation ? isNotEnabledMessage(selectionName) : isEnabledMessage(selectionName));
        return true;
    }

    @Override
    public boolean isSelected() {
        Predicate<FluentControl> isSelected = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return condition().isSelected();
            }
        };
        until(wait, isSelected, negation ? isNotSelectedMessage(selectionName) : isSelectedMessage(selectionName));
        return true;
    }

    @Override
    public boolean isClickable() {
        Predicate<FluentControl> isClickable = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return condition().isClickable();
            }
        };
        until(wait, isClickable, negation ? isNotClickableMessage(selectionName) : isClickableMessage(selectionName));
        return true;
    }

    @Override
    public boolean isStale() {
        Predicate<FluentControl> isStale = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return condition().isStale();
            }
        };
        until(wait, isStale, negation ? isNotStaleMessage(selectionName) : isStaleMessage(selectionName));
        return true;
    }

    @Override
    public StringConditions text() {
        return new FluentWaitStringMatcher(this, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return find().one().text();
            }
        });
    }

    @Override
    public boolean text(String anotherString) {
        return text().equals(anotherString);
    }

    @Override
    public StringConditions textContent() {
        return new FluentWaitStringMatcher(this, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return find().one().textContent();
            }
        });
    }

    @Override
    public boolean textContext(String anotherString) {
        return textContent().equals(anotherString);
    }

    @Override
    public RectangleConditions hasRectangle() {
        return new FluentWaitRectangleMatcher(this, new Supplier<RectangleConditions>() {
            @Override
            public RectangleConditions get() {
                return find().one().hasRectangle();
            }
        });
    }
}
