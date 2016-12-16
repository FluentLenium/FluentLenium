package org.fluentlenium.core.conditions;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Rectangle;

import java.util.function.Function;

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
    public RectangleListConditionsImpl(Conditions<FluentWebElement> conditions,
            Function<FluentWebElement, Rectangle> objectGetter,
            Function<FluentWebElement, RectangleConditions> conditionsGetter) {
        super(conditions, objectGetter, conditionsGetter);
    }

    /**
     * Creates a new list of rectangles conditions, using rectangle of the conditions element
     *
     * @param conditions rectangles conditions
     */
    public RectangleListConditionsImpl(Conditions<FluentWebElement> conditions) {
        this(conditions, input -> input.getElement().getRect(), input -> input.conditions().rectangle());
    }

    @Override
    public RectangleListConditionsImpl not() {
        return new RectangleListConditionsImpl(conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean x(int x) {
        return conditions.verify(input -> conditionsGetter.apply(input).x(x));
    }

    @Override
    public boolean y(int y) {
        return conditions.verify(input -> conditionsGetter.apply(input).y(y));
    }

    @Override
    public IntegerConditions x() {
        return new IntegerListConditionsImpl(conditions, input -> input.getElement().getRect().getX());
    }

    @Override
    public IntegerConditions y() {
        return new IntegerListConditionsImpl(conditions, input -> input.getElement().getRect().getY());
    }

    @Override
    public boolean position(int x, int y) {
        return conditions.verify(input -> conditionsGetter.apply(input).position(x, y));
    }

    @Override
    public boolean width(int width) {
        return conditions.verify(input -> conditionsGetter.apply(input).width(width));
    }

    @Override
    public IntegerConditions width() {
        return new IntegerListConditionsImpl(conditions, input -> input.getElement().getRect().getWidth());
    }

    @Override
    public boolean height(int height) {
        return conditions.verify(input -> conditionsGetter.apply(input).height(height));
    }

    @Override
    public IntegerConditions height() {
        return new IntegerListConditionsImpl(conditions, input -> input.getElement().getRect().getHeight());
    }

    @Override
    public boolean dimension(int width, int height) {
        return conditions.verify(input -> conditionsGetter.apply(input).dimension(width, height));
    }

    @Override
    public boolean positionAndDimension(int x, int y, int width, int height) {
        return conditions.verify(input -> conditionsGetter.apply(input).positionAndDimension(x, y, width, height));
    }
}
