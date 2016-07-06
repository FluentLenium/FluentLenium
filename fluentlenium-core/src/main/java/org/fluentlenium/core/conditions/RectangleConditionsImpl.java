package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import org.openqa.selenium.Rectangle;

/**
 * Conditions implementation for rectangle.
 */
public class RectangleConditionsImpl implements RectangleConditions {
    private final Rectangle rectangle;
    private boolean negation = false;

    public RectangleConditionsImpl(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public boolean isVerified(Predicate<Rectangle> predicate) {
        boolean predicateResult = predicate.apply(rectangle);
        if (negation) {
            predicateResult = !predicateResult;
        }
        return predicateResult;
    }

    @Override
    public RectangleConditionsImpl not() {
        RectangleConditionsImpl negatedConditions = new RectangleConditionsImpl(rectangle);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    @Override
    public boolean withX(final int x) {
        return isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return rectangle.getX() == x;
            }
        });
    }

    @Override
    public IntegerConditions withX() {
        return new IntegerConditionsImpl(rectangle.getX());
    }

    @Override
    public boolean withY(final int y) {
        return isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return rectangle.getY() == y;
            }
        });
    }

    @Override
    public IntegerConditions withY() {
        return new IntegerConditionsImpl(rectangle.getY());
    }

    @Override
    public boolean withPosition(final int x, final int y) {
        return isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return rectangle.getX() == x && rectangle.getY() == y;
            }
        });
    }

    @Override
    public boolean withWidth(final int width) {
        return isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return rectangle.getWidth() == width;
            }
        });
    }

    @Override
    public IntegerConditions withWidth() {
        return new IntegerConditionsImpl(rectangle.getWidth());
    }

    @Override
    public boolean withHeight(final int height) {
        return isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return rectangle.getHeight() == height;
            }
        });
    }

    @Override
    public IntegerConditions withHeight() {
        return new IntegerConditionsImpl(rectangle.getHeight());
    }

    @Override
    public boolean withDimension(final int width, final int height) {
        return isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return rectangle.getWidth() == width && rectangle.getHeight() == height;
            }
        });
    }

    @Override
    public boolean withPositionAndDimension(final int x, final int y, final int width, final int height) {
        return isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return rectangle.getX() == x && rectangle.getY() == y && rectangle.getWidth() == width && rectangle.getHeight() == height;
            }
        });
    }
}
