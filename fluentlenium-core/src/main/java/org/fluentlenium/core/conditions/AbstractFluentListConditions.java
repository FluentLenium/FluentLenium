package org.fluentlenium.core.conditions;

import java.util.List;
import java.util.function.Predicate;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * Abstract class conditions on list of elements.
 */
@SuppressWarnings("PMD.ExcessivePublicCount")
public abstract class AbstractFluentListConditions implements FluentListConditions, ListConditionsElements {
    private final FluentList<? extends FluentWebElement> elements;
    private boolean negation;

    /**
     * Creates a new conditions on list of elements.
     *
     * @param elements underlying elements
     */
    protected AbstractFluentListConditions(List<? extends FluentWebElement> elements) {
        this.elements = (FluentList<? extends FluentWebElement>) elements;
    }

    @Override
    public boolean size(int size) {
        if (negation) {
            return elements.size() != size;
        }
        return elements.size() == size;
    }

    /**
     * Is this conditions list negated ?
     *
     * @return true if this conditions list is negated, false otherwise.
     */
    protected boolean isNegation() {
        return negation;
    }

    /**
     * Set negation value
     *
     * @param negation negation value
     */
    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    /**
     * Get the underlying list of elements
     *
     * @return underlying list of elements
     */
    protected List<? extends FluentWebElement> getElements() {
        return elements;
    }

    @Override
    public List<? extends FluentWebElement> getActualElements() {
        return elements;
    }

    @Override
    public DynamicIntegerConditions size() {
        return new DynamicIntegerConditionsImpl(() -> elements, negation);
    }

    @Override
    public boolean verify(Predicate<FluentWebElement> predicate) {
        return verify(predicate, false);
    }

    @Override
    public boolean present() {
        return verify(input -> input.conditions().present(), false);
    }

    @Override
    public boolean clickable() {
        return verify(input -> input.conditions().clickable(), false);
    }

    @Override
    public boolean stale() {
        return verify(input -> input.conditions().stale(), false);
    }

    @Override
    public boolean displayed() {
        return verify(input -> input.conditions().displayed(), false);
    }

    @Override
    public boolean enabled() {
        return verify(input -> input.conditions().enabled(), false);
    }

    @Override
    public boolean selected() {
        return verify(input -> input.conditions().selected(), false);
    }

    @Override
    public boolean attribute(String name, String value) {
        return attribute(name).equalTo(value);
    }

    @Override
    public StringConditions attribute(String name) {
        return new StringListConditionsImpl(this, input -> input.attribute(name), input -> input.conditions().attribute(name));
    }

    @Override
    public boolean id(String id) {
        return id().equalTo(id);
    }

    @Override
    public StringConditions id() {
        return new StringListConditionsImpl(this, FluentWebElement::id, input -> input.conditions().id());
    }

    @Override
    public StringConditions name() {
        return new StringListConditionsImpl(this, FluentWebElement::name, input -> input.conditions().name());
    }

    @Override
    public boolean name(String name) {
        return name().equalTo(name);
    }

    @Override
    public StringConditions tagName() {
        return new StringListConditionsImpl(this, FluentWebElement::tagName, input -> input.conditions().tagName());
    }

    @Override
    public boolean tagName(String tagName) {
        return tagName().equalTo(tagName);
    }

    @Override
    public StringConditions value() {
        return new StringListConditionsImpl(this, FluentWebElement::value, input -> input.conditions().value());
    }

    @Override
    public boolean value(String value) {
        return value().equalTo(value);
    }

    @Override
    public StringConditions text() {
        return new StringListConditionsImpl(this, FluentWebElement::text, input -> input.conditions().text());
    }

    @Override
    public boolean text(String text) {
        return text().equalTo(text);
    }

    @Override
    public StringConditions textContent() {
        return new StringListConditionsImpl(this, FluentWebElement::textContent, input -> input.conditions().textContent());
    }

    @Override
    public boolean textContent(String anotherString) {
        return textContent().equalTo(anotherString);
    }

    @Override
    public RectangleConditions rectangle() {
        return new RectangleListConditionsImpl(this);
    }

    @Override
    public boolean className(String className) {
        return verify(input -> input.conditions().className(className), false);
    }
}
