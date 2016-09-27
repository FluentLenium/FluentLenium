package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import org.openqa.selenium.Rectangle;

/**
 * Conditions implementation for rectangle.
 */
public class RectangleConditionsImpl extends AbstractObjectConditions<Rectangle> implements RectangleConditions {
    public RectangleConditionsImpl(Rectangle rectangle) {
        super(rectangle);
    }

    public RectangleConditionsImpl(Rectangle object, boolean negation) {
        super(object, negation);
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
    public boolean withX(final int x) {
        return verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.getX() == x;
            }
        });
    }

    @Override
    public IntegerConditions withX() {
        return new IntegerConditionsImpl(object.getX(), negation);
    }

    @Override
    public boolean withY(final int y) {
        return verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.getY() == y;
            }
        });
    }

    @Override
    public IntegerConditions withY() {
        return new IntegerConditionsImpl(object.getY(), negation);
    }

    @Override
    public boolean withPosition(final int x, final int y) {
        return verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.getX() == x && input.getY() == y;
            }
        });
    }

    @Override
    public boolean withWidth(final int width) {
        return verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.getWidth() == width;
            }
        });
    }

    @Override
    public IntegerConditions withWidth() {
        return new IntegerConditionsImpl(object.getWidth(), negation);
    }

    @Override
    public boolean withHeight(final int height) {
        return verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return object.getHeight() == height;
            }
        });
    }

    @Override
    public IntegerConditions withHeight() {
        return new IntegerConditionsImpl(object.getHeight(), negation);
    }

    @Override
    public boolean withDimension(final int width, final int height) {
        return verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.getWidth() == width && input.getHeight() == height;
            }
        });
    }

    @Override
    public boolean withPositionAndDimension(final int x, final int y, final int width, final int height) {
        return verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.getX() == x && input.getY() == y && input.getWidth() == width && input.getHeight() == height;
            }
        });
    }
}
