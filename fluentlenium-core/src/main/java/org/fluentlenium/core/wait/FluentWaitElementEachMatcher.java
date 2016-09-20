package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.IntegerConditions;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.StringConditions;
import org.fluentlenium.core.domain.FluentWebElement;

import static org.fluentlenium.core.wait.FluentWaitMessages.hasNameMessage;
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
    public boolean verify(final Predicate<FluentWebElement> predicate) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().verify(predicate);
            }
        }, matcher.negation ? isPredicateNotVerifiedMessage(matcher.selectionName) : isPredicateVerifiedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean clickable() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().clickable();
            }
        }, matcher.negation ? isNotClickableMessage(matcher.selectionName) : isClickableMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean stale() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().stale();
            }
        }, matcher.negation ? isNotStaleMessage(matcher.selectionName) : isStaleMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean displayed() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().displayed();
            }
        }, matcher.negation ? isNotDisplayedMessage(matcher.selectionName) : isDisplayedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean enabled() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().enabled();
            }
        }, matcher.negation ? isNotEnabledMessage(matcher.selectionName) : isEnabledMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean selected() {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().selected();
            }
        }, matcher.negation ? isNotSelectedMessage(matcher.selectionName) : isSelectedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public boolean attribute(final String attribute, final String value) {
        return attribute(attribute).equals(value);
    }

    @Override
    public StringConditions attribute(final String name) {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return matcher.find().each().attribute(name);
            }
        });
    }

    @Override
    public boolean id(final String id) {
        return id().equals(id);
    }

    @Override
    public StringConditions id() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return matcher.find().each().id();
            }
        });
    }

    @Override
    public boolean name(final String name) {
        matcher.until(matcher.wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return eachCondition().name(name);
            }
        }, matcher.negation ? hasNotNameMessage(matcher.selectionName, name) : hasNameMessage(matcher.selectionName, name));
        return true;
    }

    @Override
    public StringConditions name() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return matcher.find().each().name();
            }
        });
    }

    @Override
    public boolean tagName(String tagName) {
        return tagName().equals(tagName);
    }

    @Override
    public StringConditions tagName() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return matcher.find().each().tagName();
            }
        });
    }

    @Override
    public boolean value(String value) {
        return value().equals(value);
    }

    @Override
    public StringConditions value() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return matcher.find().each().value();
            }
        });
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
    public RectangleConditions rectangle() {
        return new FluentWaitRectangleMatcher(matcher, new Supplier<RectangleConditions>() {
            @Override
            public RectangleConditions get() {
                return matcher.find().each().rectangle();
            }
        });
    }
}
