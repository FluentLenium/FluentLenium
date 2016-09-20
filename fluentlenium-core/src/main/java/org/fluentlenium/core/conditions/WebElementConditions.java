package org.fluentlenium.core.conditions;

import com.google.common.base.Objects;
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
    public boolean isClickable() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isClickable();
            }
        });
    }

    @Override
    public boolean isStale() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isStale();
            }
        });
    }

    @Override
    public boolean isDisplayed() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isDisplayed();
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isEnabled();
            }
        });
    }

    @Override
    public boolean isSelected() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isSelected();
            }
        });
    }

    @Override
    public boolean hasAttribute(final String attribute, final String value) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                String elementValue = input.getAttribute(attribute);
                return Objects.equal(elementValue, value);
            }
        });
    }

    @Override
    public boolean hasId(final String id) {
        return id().equals(id);
    }

    @Override
    public StringConditions id() {
        return new StringConditionsImpl(object.getId());
    }

    @Override
    public boolean hasName(final String name) {
        return name().equals(name);
    }

    @Override
    public StringConditions name() {
        return new StringConditionsImpl(object.getName());
    }

    @Override
    public boolean tagName(String tagName) {
        return tagName().equals(tagName);
    }

    @Override
    public StringConditions tagName() {
        return new StringConditionsImpl(object.getTagName());
    }


    @Override
    public boolean value(String value) {
        return value().equals(value);
    }

    @Override
    public StringConditions value() {
        return new StringConditionsImpl(object.getValue());
    }

    @Override
    public boolean text(String anotherString) {
        return text().equals(anotherString);
    }

    @Override
    public StringConditions text() {
        StringConditionsImpl conditions = new StringConditionsImpl(object.getText());
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
        StringConditionsImpl conditions = new StringConditionsImpl(object.getTextContent());
        if (negation) {
            conditions = conditions.not();
        }
        return conditions;
    }

    @Override
    public RectangleConditions hasRectangle() {
        RectangleConditionsImpl conditions = new RectangleConditionsImpl(object.getElement().getRect());
        if (negation) {
            conditions = conditions.not();
        }
        return conditions;
    }
}
