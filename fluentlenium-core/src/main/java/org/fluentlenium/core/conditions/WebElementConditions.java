package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Fluent object to handle {@link org.openqa.selenium.support.ui.ExpectedConditions} on FluentWebElement in fluentlenium API.
 */
public class WebElementConditions extends AbstractObjectConditions<FluentWebElement> implements FluentConditions {
    public WebElementConditions(FluentWebElement element) {
        super(element);
    }

    @Override
    protected AbstractObjectConditions<FluentWebElement> newInstance() {
        return new WebElementConditions(object);
    }

    @Override
    public WebElementConditions not() {
        return (WebElementConditions) super.not();
    }

    @Override
    public boolean clickable() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.clickable();
            }
        });
    }

    @Override
    public boolean stale() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.stale();
            }
        });
    }

    @Override
    public boolean displayed() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.displayed();
            }
        });
    }

    @Override
    public boolean enabled() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.enabled();
            }
        });
    }

    @Override
    public boolean selected() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.selected();
            }
        });
    }

    @Override
    public boolean attribute(final String name, final String value) {
        return attribute(name).equals(value);
    }

    @Override
    public StringConditions attribute(String name) {
        return new StringConditionsImpl(object.attribute(name));
    }

    @Override
    public boolean id(final String id) {
        return id().equals(id);
    }

    @Override
    public StringConditions id() {
        return new StringConditionsImpl(object.id());
    }

    @Override
    public boolean name(final String name) {
        return name().equals(name);
    }

    @Override
    public StringConditions name() {
        return new StringConditionsImpl(object.name());
    }

    @Override
    public boolean tagName(String tagName) {
        return tagName().equals(tagName);
    }

    @Override
    public StringConditions tagName() {
        return new StringConditionsImpl(object.tagName());
    }


    @Override
    public boolean value(String value) {
        return value().equals(value);
    }

    @Override
    public StringConditions value() {
        return new StringConditionsImpl(object.value());
    }

    @Override
    public boolean text(String anotherString) {
        return text().equals(anotherString);
    }

    @Override
    public StringConditions text() {
        StringConditionsImpl conditions = new StringConditionsImpl(object.text());
        if (negation) {
            conditions = conditions.not();
        }
        return conditions;
    }

    @Override
    public boolean textContext(String anotherString) {
        return textContent().equals(anotherString);
    }

    @Override
    public StringConditions textContent() {
        StringConditionsImpl conditions = new StringConditionsImpl(object.textContent());
        if (negation) {
            conditions = conditions.not();
        }
        return conditions;
    }

    @Override
    public RectangleConditions rectangle() {
        RectangleConditionsImpl conditions = new RectangleConditionsImpl(object.getElement().getRect());
        if (negation) {
            conditions = conditions.not();
        }
        return conditions;
    }
}
