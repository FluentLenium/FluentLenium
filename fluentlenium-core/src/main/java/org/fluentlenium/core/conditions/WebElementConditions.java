package org.fluentlenium.core.conditions;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Fluent object to handle {@link org.openqa.selenium.support.ui.ExpectedConditions} on FluentWebElement in fluentlenium API.
 */
public class WebElementConditions implements FluentConditions {
    private FluentWebElement element;
    private boolean negation;

    public WebElementConditions(FluentWebElement element) {
        this.element = element;
    }

    @Override
    public WebElementConditions not() {
        WebElementConditions negatedConditions = new WebElementConditions(element);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    @Override
    public boolean isVerified(Predicate<FluentWebElement> predicate) {
        boolean predicateResult = predicate.apply(element);
        if (negation) {
            predicateResult = !predicateResult;
        }
        return predicateResult;
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
                return element.isDisplayed();
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return element.isEnabled();
            }
        });
    }

    @Override
    public boolean isSelected() {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return element.isSelected();
            }
        });
    }

    @Override
    public boolean hasText(final String text) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                String elementText = element.getText();
                return Objects.equal(elementText, text);
            }
        });
    }

    @Override
    public boolean containsText(final String text) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                String elementText = element.getText();
                if (elementText == null && text != null) {
                    return false;
                }
                return elementText.contains(text);
            }
        });
    }

    @Override
    public boolean hasAttribute(final String attribute, final String value) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                String elementValue = element.getAttribute(attribute);
                return Objects.equal(elementValue, value);
            }
        });
    }

    @Override
    public boolean hasId(final String id) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                String elementId = element.getId();
                return Objects.equal(elementId, id);
            }
        });

    }

    @Override
    public boolean hasName(final String name) {
        return isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                String elementName = element.getName();
                return Objects.equal(elementName, name);
            }
        });
    }

    @Override
    public RectangleConditions hasRectangle() {
        RectangleConditionsImpl conditions = new RectangleConditionsImpl(element.getElement().getRect());
        if (negation) {
            conditions = conditions.not();
        }
        return conditions;
    }
}
