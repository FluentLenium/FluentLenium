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

    protected FluentListConditions conditions() {
        return conditions(false);
    }

    protected FluentListConditions conditions(boolean ignoreNot) {
        FluentListConditions conditions = find().one();
        if (!ignoreNot && negation) {
            conditions = conditions.not();
        }
        return conditions;
    }

    @Override
    public boolean verify(final Predicate<FluentWebElement> predicate) {
        until(wait, new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().verify(predicate);
            }
        }, negation ? isPredicateNotVerifiedMessage(selectionName) : isPredicateVerifiedMessage(selectionName));
        return true;
    }

    /**
     * Check that one or more element is present
     *
     * @return true if one or more element is present, false otherwise
     */
    public boolean present() {
        Predicate<FluentControl> present = new com.google.common.base.Predicate<FluentControl>() {
            public boolean apply(FluentControl fluent) {
                return conditions().isPresent();
            }
        };
        until(wait, present, negation ? isNotPresentMessage(selectionName) : isPresentMessage(selectionName));
        return true;
    }

    @Override
    public boolean displayed() {
        Predicate<FluentControl> isDisplayed = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().displayed();
            }
        };
        until(wait, isDisplayed, negation ? isNotDisplayedMessage(selectionName) : isDisplayedMessage(selectionName));
        return true;
    }

    @Override
    public boolean enabled() {
        Predicate<FluentControl> isEnabled = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().enabled();
            }
        };
        until(wait, isEnabled, negation ? isNotEnabledMessage(selectionName) : isEnabledMessage(selectionName));
        return true;
    }

    @Override
    public boolean selected() {
        Predicate<FluentControl> isSelected = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().selected();
            }
        };
        until(wait, isSelected, negation ? isNotSelectedMessage(selectionName) : isSelectedMessage(selectionName));
        return true;
    }

    @Override
    public boolean clickable() {
        Predicate<FluentControl> isClickable = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().clickable();
            }
        };
        until(wait, isClickable, negation ? isNotClickableMessage(selectionName) : isClickableMessage(selectionName));
        return true;
    }

    @Override
    public boolean stale() {
        Predicate<FluentControl> isStale = new Predicate<FluentControl>() {
            @Override
            public boolean apply(FluentControl input) {
                return conditions().stale();
            }
        };
        until(wait, isStale, negation ? isNotStaleMessage(selectionName) : isStaleMessage(selectionName));
        return true;
    }


    @Override
    public boolean attribute(final String name, final String value) {
        return attribute(name).equals(value);
    }

    @Override
    public StringConditions attribute(final String name) {
        return new FluentWaitStringMatcher(this, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return conditions(true).attribute(name);
            }
        });
    }

    @Override
    public boolean id(final String value) {
        return id().equals(value);
    }

    @Override
    public StringConditions id() {
        return new FluentWaitStringMatcher(this, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return conditions(true).id();
            }
        });
    }

    @Override
    public boolean name(final String value) {
        return name().equals(value);
    }

    @Override
    public StringConditions name() {
        return new FluentWaitStringMatcher(this, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return conditions(true).name();
            }
        });
    }

    @Override
    public boolean tagName(String tagName) {
        return tagName().equals(tagName);
    }

    @Override
    public StringConditions tagName() {
        return new FluentWaitStringMatcher(this, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return conditions(true).tagName();
            }
        });
    }

    @Override
    public StringConditions text() {
        return new FluentWaitStringMatcher(this, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return conditions(true).text();
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
                return conditions(true).textContent();
            }
        });
    }

    @Override
    public boolean textContext(String anotherString) {
        return textContent().equals(anotherString);
    }

    @Override
    public boolean value(String value) {
        return value().equals(value);
    }

    @Override
    public StringConditions value() {
        return new FluentWaitStringMatcher(this, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return conditions(true).value();
            }
        });
    }

    @Override
    public RectangleConditions rectangle() {
        return new FluentWaitRectangleMatcher(this, new Supplier<RectangleConditions>() {
            @Override
            public RectangleConditions get() {
                return conditions(true).rectangle();
            }
        });
    }
}
