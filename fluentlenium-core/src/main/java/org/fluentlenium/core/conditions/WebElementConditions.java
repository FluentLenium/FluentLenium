package org.fluentlenium.core.conditions;

import org.fluentlenium.core.domain.FluentWebElement;

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

    @Override
    public boolean className(final String className) {

        final FluentWebElement element = getActualObject();
        final String classAttribute = element.attribute("class");

        if (classAttribute == null) {
            return false;
        }

        final String[] classes = classAttribute.split(" ");
        return Arrays.asList(classes).contains(className);
    }
}
