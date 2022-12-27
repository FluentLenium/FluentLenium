package io.fluentlenium.core.conditions;

import io.fluentlenium.core.domain.FluentWebElement;

import java.util.Arrays;

/**
 * Fluent object to handle {@link org.openqa.selenium.support.ui.ExpectedConditions} on FluentWebElement in fluentlenium API.
 */
public class WebElementConditions extends AbstractObjectConditions<FluentWebElement> implements FluentConditions {
    /**
     * Creates a new conditions object on element
     *
     * @param element underlying element
     */
    public WebElementConditions(FluentWebElement element) {
        super(element);
    }

    /**
     * Creates a new conditions object on element
     *
     * @param element  underlying element
     * @param negation negation value
     */
    public WebElementConditions(FluentWebElement element, boolean negation) {
        super(element, negation);
    }

    @Override
    protected AbstractObjectConditions<FluentWebElement> newInstance(boolean negationValue) {
        return new WebElementConditions(object, negationValue);
    }

    @Override
    @Negation
    public WebElementConditions not() {
        return (WebElementConditions) super.not();
    }

    @Override
    public boolean present() {
        return verify(FluentWebElement::present);
    }

    @Override
    public boolean clickable() {
        return verify(FluentWebElement::clickable);
    }

    @Override
    public boolean stale() {
        return verify(FluentWebElement::stale);
    }

    @Override
    public boolean displayed() {
        return verify(FluentWebElement::displayed);
    }

    @Override
    public boolean enabled() {
        return verify(FluentWebElement::enabled);
    }

    @Override
    public boolean selected() {
        return verify(FluentWebElement::selected);
    }

    @Override
    public StringConditions attribute(String name) {
        return new StringConditionsImpl(object.attribute(name), negation);
    }

    @Override
    public StringConditions id() {
        return new StringConditionsImpl(object.id(), negation);
    }

    @Override
    public StringConditions name() {
        return new StringConditionsImpl(object.name(), negation);
    }

    @Override
    public StringConditions tagName() {
        return new StringConditionsImpl(object.tagName(), negation);
    }

    @Override
    public StringConditions value() {
        return new StringConditionsImpl(object.value(), negation);
    }

    @Override
    public StringConditions text() {
        return new StringConditionsImpl(object.text(), negation);
    }

    @Override
    public StringConditions textContent() {
        return new StringConditionsImpl(object.textContent(), negation);
    }

    @Override
    public RectangleConditions rectangle() {
        return new RectangleConditionsImpl(object.getElement().getRect(), negation);
    }

    @Override
    public boolean className(String className) {
        FluentWebElement element = getActualObject();
        String classAttribute = element.attribute("class");

        if (classAttribute == null) {
            return false;
        }

        String[] classes = classAttribute.split(" ");
        return Arrays.asList(classes).contains(className);
    }
}
