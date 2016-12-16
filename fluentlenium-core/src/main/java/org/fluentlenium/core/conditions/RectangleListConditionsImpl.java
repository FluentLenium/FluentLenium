package org.fluentlenium.core.conditions;

import java.util.function.Function;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Rectangle;

/**
 * Conditions for list of rectangles.
 */
public class RectangleListConditionsImpl extends BaseObjectListConditions<Rectangle, RectangleConditions>
        implements RectangleConditions {

    /**
     * Creates a new list of rectangles conditions
     *
     * @param conditions       rectangles conditions
     * @param objectGetter     getter of the underlying rectangle
     * @param conditionsGetter getter of the underlying rectangle conditions
     */
    public RectangleListConditionsImpl(final Conditions<FluentWebElement> conditions,
            final Function<FluentWebElement, Rectangle> objectGetter,
            final Function<FluentWebElement, RectangleConditions> conditionsGetter) {
        super(conditions, objectGetter, conditionsGetter);
    }

    /**
     * Creates a new list of rectangles conditions, using rectangle of the conditions element
     *
     * @param conditions rectangles conditions
     */
    public RectangleListConditionsImpl(final Conditions<FluentWebElement> conditions) {
        this(conditions, input -> input.getElement().getRect(), input -> input.conditions().rectangle());
    }

    @Override
    public RectangleListConditionsImpl not() {
        return new RectangleListConditionsImpl(this.conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean x(final int x) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).x(x));
    }

    @Override
    public boolean y(final int y) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).y(y));
    }

    @Override
    public IntegerConditions x() {
        return new IntegerListConditionsImpl(this.conditions, input -> input.getElement().getRect().getX());
    }

    @Override
    public IntegerConditions y() {
        return new IntegerListConditionsImpl(this.conditions, input -> input.getElement().getRect().getY());
    }

    @Override
    public boolean position(final int x, final int y) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).position(x, y));
    }

    @Override
    public boolean width(final int width) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).width(width));
    }

    @Override
    public IntegerConditions width() {
        return new IntegerListConditionsImpl(this.conditions, input -> input.getElement().getRect().getWidth());
    }

    @Override
    public boolean height(final int height) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).height(height));
    }

    @Override
    public IntegerConditions height() {
        return new IntegerListConditionsImpl(this.conditions, input -> input.getElement().getRect().getHeight());
    }

    @Override
    public boolean dimension(final int width, final int height) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).dimension(width, height));
    }

    @Override
    public boolean positionAndDimension(final int x, final int y, final int width, final int height) {
        return this.conditions.verify(input -> conditionsGetter.apply(input).positionAndDimension(x, y, width, height));
    }
}
