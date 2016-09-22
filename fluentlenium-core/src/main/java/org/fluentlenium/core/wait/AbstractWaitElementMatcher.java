package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.StringConditions;
import org.fluentlenium.core.conditions.message.MessageProxy;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

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
        return applyNegation(conditions, ignoreNot);
    }

    protected FluentListConditions applyNegation(FluentListConditions conditions, boolean ignoreNot) {
        if (!ignoreNot && negation) return conditions.not();
        return conditions;
    }

    protected FluentListConditions messageBuilder() {
        return messageBuilder(false);
    }

    protected FluentListConditions messageBuilder(boolean ignoreNot) {
        FluentListConditions conditions = MessageProxy.builder(FluentListConditions.class, selectionName);
        applyNegation(conditions, ignoreNot);
        return conditions;
    }

    @Override
    public boolean verify(final Predicate<FluentWebElement> predicate) {
        until(wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.verify(predicate);
            }
        });
        return true;
    }

    /**
     * Check that one or more element is present
     *
     * @return true if one or more element is present, false otherwise
     */
    public boolean present() {
        until(wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.present();
            }
        });
        return true;
    }

    @Override
    public boolean displayed() {
        until(wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.displayed();
            }
        });
        return true;
    }

    @Override
    public boolean enabled() {
        until(wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.enabled();
            }
        });
        return true;
    }

    @Override
    public boolean selected() {
        until(wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.selected();
            }
        });
        return true;
    }

    @Override
    public boolean clickable() {
        until(wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.clickable();
            }
        });
        return true;
    }

    @Override
    public boolean stale() {
        until(wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.stale();
            }
        });
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
        }, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return messageBuilder(true).attribute(name);
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
        }, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return messageBuilder(true).id();
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
        }, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return messageBuilder(true).name();
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
        }, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return messageBuilder(true).tagName();
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
        }, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return messageBuilder(true).text();
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
        }, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return messageBuilder(true).textContent();
            }
        });
    }

    @Override
    public boolean textContent(String anotherString) {
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
        }, new Supplier<StringConditions>() {
            @Override
            public StringConditions get() {
                return messageBuilder(true).value();
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
        }, new Supplier<RectangleConditions>() {
            @Override
            public RectangleConditions get() {
                return messageBuilder(true).rectangle();
            }
        });
    }
}
