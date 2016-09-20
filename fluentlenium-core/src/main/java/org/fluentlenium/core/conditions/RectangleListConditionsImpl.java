package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Rectangle;

/**
 * Conditions implement for rectangles of list of elements.
 */
public class RectangleListConditionsImpl extends AbstractObjectListConditions<Rectangle, RectangleConditions> implements RectangleConditions {
    public RectangleListConditionsImpl(Conditions<FluentWebElement> listConditions,
                                       Function<FluentWebElement, Rectangle> rectangleGetter,
                                       Function<FluentWebElement, RectangleConditions> conditionsGetter) {
        super(listConditions, rectangleGetter, conditionsGetter);
    }

    public RectangleListConditionsImpl(Conditions<FluentWebElement> listConditions) {
        this(listConditions, new Function<FluentWebElement, Rectangle>() {
            @Override
            public Rectangle apply(FluentWebElement input) {
                return input.getElement().getRect();
            }
        }, new Function<FluentWebElement, RectangleConditions>() {
            @Override
            public RectangleConditions apply(FluentWebElement input) {
                return input.conditions().hasRectangle();
            }
        });
    }

    @Override
    public RectangleListConditionsImpl not() {
        return new RectangleListConditionsImpl(this.conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean withX(final int x) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withX(x);
            }
        });
    }

    @Override
    public boolean withY(final int y) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withY(y);
            }
        });
    }

    @Override
    public IntegerConditions withX() {
        return new IntegerListConditionsImpl(this.conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getX();
            }
        });
    }

    @Override
    public IntegerConditions withY() {
        return new IntegerListConditionsImpl(this.conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getY();
            }
        });
    }

    @Override
    public boolean withPosition(final int x, final int y) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withPosition(x, y);
            }
        });
    }

    @Override
    public boolean withWidth(final int width) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withWidth(width);
            }
        });
    }

    @Override
    public IntegerConditions withWidth() {
        return new IntegerListConditionsImpl(this.conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getWidth();
            }
        });
    }

    @Override
    public boolean withHeight(final int height) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withHeight(height);
            }
        });
    }

    @Override
    public IntegerConditions withHeight() {
        return new IntegerListConditionsImpl(this.conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getHeight();
            }
        });
    }

    @Override
    public boolean withDimension(final int width, final int height) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withDimension(width, height);
            }
        });
    }

    @Override
    public boolean withPositionAndDimension(final int x, final int y, final int width, final int height) {
        return this.conditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withPositionAndDimension(x, y, width, height);
            }
        });
    }
}
