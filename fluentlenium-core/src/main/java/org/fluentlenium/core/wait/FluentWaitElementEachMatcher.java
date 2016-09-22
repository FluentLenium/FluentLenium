package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.IntegerConditions;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.StringConditions;
import org.fluentlenium.core.conditions.message.MessageProxy;
import org.fluentlenium.core.domain.FluentWebElement;

public class FluentWaitElementEachMatcher implements FluentListConditions {
    private final AbstractWaitElementListMatcher matcher;

    FluentWaitElementEachMatcher(AbstractWaitElementListMatcher matcher) {
        this.matcher = matcher;
    }

    protected FluentListConditions conditions() {
        return conditions(false);
    }

    protected FluentListConditions conditions(boolean ignoreNot) {
        FluentListConditions conditions = matcher.find().each();
        return matcher.applyNegation(conditions, ignoreNot);
    }

    protected FluentListConditions messageBuilder() {
        return messageBuilder(false);
    }

    protected FluentListConditions messageBuilder(boolean ignoreNot) {
        FluentListConditions conditions = MessageProxy.builder(FluentListConditions.class, matcher.selectionName);
        return matcher.applyNegation(conditions, ignoreNot);
    }

    @Override
    public FluentWaitElementEachMatcher not() {
        return new FluentWaitElementEachMatcher((AbstractWaitElementListMatcher) matcher.not());
    }

    @Override
    public boolean verify(final Predicate<FluentWebElement> predicate, final boolean defaultValue) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.verify(predicate, defaultValue);
            }
        });
        return true;
    }

    @Override
    public boolean verify(final Predicate<FluentWebElement> predicate) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.verify(predicate);
            }
        });
        return true;
    }

    @Override
    public boolean clickable() {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.clickable();
            }
        });
        return true;
    }

    @Override
    public boolean stale() {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.stale();
            }
        });
        return true;
    }

    @Override
    public boolean displayed() {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.displayed();
            }
        });
        return true;
    }

    @Override
    public boolean enabled() {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.enabled();
            }
        });
        return true;
    }

    @Override
    public boolean selected() {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.selected();
            }
        });
        return true;
    }

    @Override
    public boolean attribute(final String name, final String value) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.attribute(name, value);
            }
        });
        return true;
    }

    @Override
    public StringConditions attribute(final String name) {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
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
    public boolean id(final String id) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.id(id);
            }
        });
        return true;
    }

    @Override
    public StringConditions id() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
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
    public boolean name(final String name) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.name(name);
            }
        });
        return true;
    }

    @Override
    public StringConditions name() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
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
    public boolean tagName(final String tagName) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.tagName(tagName);
            }
        });
        return true;
    }

    @Override
    public StringConditions tagName() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
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
    public boolean value(final String value) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.value(value);
            }
        });
        return true;
    }

    @Override
    public StringConditions value() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
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
    public boolean present() {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.present();
            }
        });
        return true;
    }

    @Override
    public boolean hasSize(final int size) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.hasSize(size);
            }
        });
        return true;
    }

    @Override
    public IntegerConditions hasSize() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return conditions(true).hasSize();
            }
        }, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return messageBuilder(true).hasSize();
            }
        });
    }

    @Override
    public StringConditions text() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
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
    public boolean text(final String anotherString) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.text(anotherString);
            }
        });
        return true;
    }

    @Override
    public StringConditions textContent() {
        return new FluentWaitStringMatcher(matcher, new Supplier<StringConditions>() {
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
    public boolean textContent(final String anotherString) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<FluentListConditions, Boolean>() {
            @Override
            public Boolean apply(FluentListConditions input) {
                return input.textContent(anotherString);
            }
        });
        return true;
    }

    @Override
    public RectangleConditions rectangle() {
        return new FluentWaitRectangleMatcher(matcher, new Supplier<RectangleConditions>() {
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
