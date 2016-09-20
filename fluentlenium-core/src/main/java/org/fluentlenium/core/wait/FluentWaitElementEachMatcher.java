package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.IntegerConditions;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.StringConditions;
import org.fluentlenium.core.domain.FluentWebElement;

import static org.fluentlenium.core.wait.FluentWaitMessages.hasAttributeMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasIdMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNameMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotAttributeMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotIdMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotNameMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotSizeMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasSizeMessage;
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

public class FluentWaitElementEachMatcher implements FluentListConditions {
    private final AbstractWaitElementListMatcher matcher;

    FluentWaitElementEachMatcher(AbstractWaitElementListMatcher matcher) {
        this.matcher = matcher;
    }

    protected FluentListConditions eachCondition() {
        FluentListConditions conditions = matcher.find().each();
        if (matcher.negation) {
            conditions = conditions.not();
        }
        return conditions;
    }

    @Override
    public FluentWaitElementEachMatcher not() {
        return new FluentWaitElementEachMatcher((AbstractWaitElementListMatcher) matcher.not());
    }

    @Override
    public boolean isVerified(final Predicate<FluentWebElement> predicate, final boolean defaultValue) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().isVerified(predicate, defaultValue);
            }
        }, matcher.negation ? isPredicateNotVerifiedMessage(matcher.selectionName) : isPredicateVerifiedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean isVerified(final Predicate<FluentWebElement> predicate) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().isVerified(predicate);
            }
        }, matcher.negation ? isPredicateNotVerifiedMessage(matcher.selectionName) : isPredicateVerifiedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean isClickable() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().isClickable();
            }
        }, matcher.negation ? isNotClickableMessage(matcher.selectionName) : isClickableMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean isStale() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().isStale();
            }
        }, matcher.negation ? isNotStaleMessage(matcher.selectionName) : isStaleMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean isDisplayed() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().isDisplayed();
            }
        }, matcher.negation ? isNotDisplayedMessage(matcher.selectionName) : isDisplayedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean isEnabled() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().isEnabled();
            }
        }, matcher.negation ? isNotEnabledMessage(matcher.selectionName) : isEnabledMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean isSelected() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().isSelected();
            }
        }, matcher.negation ? isNotSelectedMessage(matcher.selectionName) : isSelectedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean hasAttribute(final String attribute, final String value) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().hasAttribute(attribute, value);
            }
        }, matcher.negation ? hasNotAttributeMessage(matcher.selectionName, attribute, value) : hasAttributeMessage(matcher.selectionName, attribute, value));
        return true;
    }

    @Override
    public boolean hasId(final String id) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().hasId(id);
            }
        }, matcher.negation ? hasNotIdMessage(matcher.selectionName, id) : hasIdMessage(matcher.selectionName, id));
        return true;
    }

    @Override
    public boolean hasName(final String name) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().hasName(name);
            }
        }, matcher.negation ? hasNotNameMessage(matcher.selectionName, name) : hasNameMessage(matcher.selectionName, name));
        return true;
    }

    @Override
    public boolean isPresent() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().isPresent();
            }
        }, matcher.negation ? isNotPresentMessage(matcher.selectionName) : isPresentMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean hasSize(final int size) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().hasSize(size);
            }
        }, matcher.negation ? hasNotSizeMessage(matcher.selectionName, size) : hasSizeMessage(matcher.selectionName, size));
        return true;
    }

    @Override
    public IntegerConditions hasSize() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return matcher.find().each().hasSize();
            }
        });
    }

    @Override
    public StringConditions text() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return matcher.find().each().text();
            }
        });
    }

    @Override
    public boolean text(String anotherString) {
        return text().equals(anotherString);
    }

    @Override
    public StringConditions textContent() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return matcher.find().each().textContent();
            }
        });
    }

    @Override
    public boolean textContext(String anotherString) {
        return textContent().equals(anotherString);
    }

    @Override
    public RectangleConditions hasRectangle() {
        return new FluentWaitRectangleMatcher(matcher, new Supplier<RectangleConditions>() {
            @Override
            public RectangleConditions get() {
                return matcher.find().each().hasRectangle();
            }
        });
    }
}
