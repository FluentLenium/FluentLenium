package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
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
    public RectangleConditionsImpl(final Rectangle rectangle) {
        super(rectangle);
    }

    /**
     * Creates a new conditions object on rectangle.
     *
     * @param rectangle underlying rectangle
     * @param negation  negation value
     */
    public RectangleConditionsImpl(final Rectangle rectangle, final boolean negation) {
        super(rectangle, negation);
    }

    @Override
    protected RectangleConditionsImpl newInstance(final boolean negationValue) {
        return new RectangleConditionsImpl(object, negationValue);
    }

    @Override
    @Negation
    public RectangleConditionsImpl not() {
        return (RectangleConditionsImpl) super.not();
    }

    @Override
    public boolean x(final int x) {
        return verify(new Predicate<Rectangle>() {

            @Override
            public boolean apply(final Rectangle input) {
                return input.getX() == x;
            }
        });
    }

    @Override
    public IntegerConditions x() {
        return new IntegerConditionsImpl(object.getX(), negation);
    }

    @Override
    public boolean y(final int y) {
        return verify(new Predicate<Rectangle>() {

            @Override
            public boolean apply(final Rectangle input) {
                return input.getY() == y;
            }
        });
    }

    @Override
    public IntegerConditions y() {
        return new IntegerConditionsImpl(object.getY(), negation);
    }

    @Override
    public boolean position(final int x, final int y) {
        return verify(new Predicate<Rectangle>() {

            @Override
            public boolean apply(final Rectangle input) {
                return input.getX() == x && input.getY() == y;
            }
        });
    }

    @Override
    public boolean width(final int width) {
        return verify(new Predicate<Rectangle>() {

            @Override
            public boolean apply(final Rectangle input) {
                return input.getWidth() == width;
            }
        });
    }

    @Override
    public IntegerConditions width() {
        return new IntegerConditionsImpl(object.getWidth(), negation);
    }

    @Override
    public boolean height(final int height) {
        return verify(new Predicate<Rectangle>() {

            @Override
            public boolean apply(final Rectangle input) {
                return object.getHeight() == height;
            }
        });
    }

    @Override
    public IntegerConditions height() {
        return new IntegerConditionsImpl(object.getHeight(), negation);
    }

    @Override
    public boolean dimension(final int width, final int height) {
        return verify(new Predicate<Rectangle>() {

            @Override
            public boolean apply(final Rectangle input) {
                return input.getWidth() == width && input.getHeight() == height;
            }
        });
    }

    @Override
    public boolean positionAndDimension(final int x, final int y, final int width, final int height) {
        return verify(new Predicate<Rectangle>() {

            @Override
            public boolean apply(final Rectangle input) {
                return input.getX() == x && input.getY() == y && input.getWidth() == width && input.getHeight() == height;
            }
        });
    }
}
