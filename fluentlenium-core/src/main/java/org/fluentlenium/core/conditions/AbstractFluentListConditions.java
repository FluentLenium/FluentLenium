package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

public abstract class AbstractFluentListConditions implements FluentListConditions {
    protected boolean negation = false;

    protected final List<? extends FluentWebElement> elements;

    public AbstractFluentListConditions(List<? extends FluentWebElement> elements) {
        this.elements = elements;
    }

    @Override
    public boolean isPresent() {
        if (negation) {
            return elements.size() <= 0;
        }
        return elements.size() > 0;
    }

    @Override
    public boolean hasSize(int size) {
        return elements.size() == size;
    }

    @Override
    public IntegerConditions hasSize() {
        return new IntegerConditionsImpl(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return elements.size();
            }
        });
    }

    @Override
    public boolean isVerified(Predicate<FluentWebElement> predicate) {
        return isVerified(predicate, false);
    }

    @Override
    public boolean isClickable() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isClickable();
            }
        }, false);
    }

    @Override
    public boolean isStale() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().isStale();
            }
        }, false);
    }

    @Override
    public boolean isDisplayed() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().isDisplayed();
            }
        }, false);
    }

    @Override
    public boolean isEnabled() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().isEnabled();
            }
        }, false);
    }

    @Override
    public boolean isSelected() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().isSelected();
            }
        }, false);
    }

    @Override
    public boolean hasText(final String text) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().hasText(text);
            }
        }, false);
    }

    @Override
    public boolean containsText(final String text) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().containsText(text);
            }
        }, false);
    }

    @Override
    public boolean hasAttribute(final String attribute, final String value) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().hasAttribute(attribute, value);
            }
        }, false);
    }


    @Override
    public boolean hasId(final String id) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().hasId(id);
            }
        }, false);
    }

    @Override
    public boolean hasName(final String name) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.conditions().hasName(name);
            }
        }, false);
    }

}
