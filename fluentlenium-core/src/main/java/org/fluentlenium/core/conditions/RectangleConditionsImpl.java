package org.fluentlenium.core.conditions;

import org.openqa.selenium.Rectangle;

/**
 * Conditions for rectangle.
 */
public class RectangleConditionsImpl extends AbstractObjectConditions<Rectangle> implements RectangleConditions {
    /**
     * Creates a new conditions object on rectangle.
     *
     * @param rectangle underlying rectangle
     */
    public RectangleConditionsImpl(Rectangle rectangle) {
        super(rectangle);
    }

    /**
     * Creates a new conditions object on rectangle.
     *
     * @param rectangle underlying rectangle
     * @param negation  negation value
     */
    public RectangleConditionsImpl(Rectangle rectangle, boolean negation) {
        super(rectangle, negation);
    }

    @Override
    protected RectangleConditionsImpl newInstance(boolean negationValue) {
        return new RectangleConditionsImpl(object, negationValue);
    }

    @Override
    @Negation
    public RectangleConditionsImpl not() {
        return (RectangleConditionsImpl) super.not();
    }

    @Override
    public boolean x(int x) {
        return verify(input -> input.getX() == x);
    }

    @Override
    public IntegerConditions x() {
        return new IntegerConditionsImpl(object.getX(), negation);
    }

    @Override
    public boolean y(int y) {
        return verify(input -> input.getY() == y);
    }

    @Override
    public IntegerConditions y() {
        return new IntegerConditionsImpl(object.getY(), negation);
    }

    @Override
    public boolean position(int x, int y) {
        return verify(input -> input.getX() == x && input.getY() == y);
    }

    @Override
    public boolean width(int width) {
        return verify(input -> input.getWidth() == width);
    }

    @Override
    public IntegerConditions width() {
        return new IntegerConditionsImpl(object.getWidth(), negation);
    }

    @Override
    public boolean height(int height) {
        return verify(input -> object.getHeight() == height);
    }

    @Override
    public IntegerConditions height() {
        return new IntegerConditionsImpl(object.getHeight(), negation);
    }

    @Override
    public boolean dimension(int width, int height) {
        return verify(input -> input.getWidth() == width && input.getHeight() == height);
    }

    @Override
    public boolean positionAndDimension(int x, int y, int width, int height) {
        return verify(input -> input.getX() == x && input.getY() == y && input.getWidth() == width
                && input.getHeight() == height);
    }
}
