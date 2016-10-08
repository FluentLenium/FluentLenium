package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Fluent object to handle {@link org.openqa.selenium.support.ui.ExpectedConditions} on FluentWebElement in fluentlenium API.
 */
public class WebElementConditions extends AbstractObjectConditions<FluentWebElement> implements FluentConditions {
    /**
     * Creates a new conditions object on element
     *
     * @param element underlying element
     */
    public WebElementConditions(final FluentWebElement element) {
        super(element);
    }

    /**
     * Creates a new conditions object on element
     *
     * @param element  underlying element
     * @param negation negation value
     */
    public WebElementConditions(final FluentWebElement element, final boolean negation) {
        super(element, negation);
    }

    @Override
    protected AbstractObjectConditions<FluentWebElement> newInstance(final boolean negationValue) {
        return new WebElementConditions(object, negationValue);
    }

    @Override
    @Negation
    public WebElementConditions not() {
        return (WebElementConditions) super.not();
    }

    @Override
    public boolean present() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.present();
            }
        });
    }

    @Override
    public boolean clickable() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.clickable();
            }
        });
    }

    @Override
    public boolean stale() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.stale();
            }
        });
    }

    @Override
    public boolean displayed() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.displayed();
            }
        });
    }

    @Override
    public boolean enabled() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.enabled();
            }
        });
    }

    @Override
    public boolean selected() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.selected();
            }
        });
    }

    @Override
    public boolean attribute(final String name, final String value) {
        return attribute(name).equalTo(value);
    }

    @Override
    public StringConditions attribute(final String name) {
        return new StringConditionsImpl(object.attribute(name), negation);
    }

    @Override
    public boolean id(final String id) {
        return id().equalTo(id);
    }

    @Override
    public StringConditions id() {
        return new StringConditionsImpl(object.id(), negation);
    }

    @Override
    public boolean name(final String name) {
        return name().equalTo(name);
    }

    @Override
    public StringConditions name() {
        return new StringConditionsImpl(object.name(), negation);
    }

    @Override
    public boolean tagName(final String tagName) {
        return tagName().equalTo(tagName);
    }

    @Override
    public StringConditions tagName() {
        return new StringConditionsImpl(object.tagName(), negation);
    }

    @Override
    public boolean value(final String value) {
        return value().equalTo(value);
    }

    @Override
    public StringConditions value() {
        return new StringConditionsImpl(object.value(), negation);
    }

    @Override
    public boolean text(final String text) {
        return text().equalTo(text);
    }

    @Override
    public StringConditions text() {
        return new StringConditionsImpl(object.text(), negation);
    }

    @Override
    public boolean textContent(final String anotherString) {
        return textContent().equalTo(anotherString);
    }

    @Override
    public StringConditions textContent() {
        return new StringConditionsImpl(object.textContent(), negation);
    }

    @Override
    public RectangleConditions rectangle() {
        return new RectangleConditionsImpl(object.getElement().getRect(), negation);
    }
}
