package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Rectangle;

/**
 * Conditions implement for rectangles of list of elements.
 */
public class RectangleListConditionsImpl implements RectangleConditions {
    private final Conditions<FluentWebElement> listConditions;
    private final Function<FluentWebElement, Rectangle> rectangleGetter;
    private final Function<FluentWebElement, RectangleConditions> conditionsGetter;

    public RectangleListConditionsImpl(Conditions<FluentWebElement> listConditions,
                                       Function<FluentWebElement, Rectangle> rectangleGetter,
                                       Function<FluentWebElement, RectangleConditions> conditionsGetter) {
        this.listConditions = listConditions;
        this.rectangleGetter = rectangleGetter;
        this.conditionsGetter = conditionsGetter;
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
    public boolean isVerified(final Predicate<Rectangle> predicate) {
        return this.listConditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return predicate.apply(rectangleGetter.apply(input));
            }
        });
    }

    @Override
    public RectangleConditions not() {
        this.listConditions.not();
        return this;
    }

    @Override
    public boolean withX(final int x) {
        return this.listConditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withX(x);
            }
        });
    }

    @Override
    public boolean withY(final int y) {
        return this.listConditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withY(y);
            }
        });
    }

    @Override
    public IntegerConditions withX() {
        return new IntegerListConditionsImpl(this.listConditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getX();
            }
        });
    }

    @Override
    public IntegerConditions withY() {
        return new IntegerListConditionsImpl(this.listConditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getY();
            }
        });
    }

    @Override
    public boolean withPosition(final int x, final int y) {
        return this.listConditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withPosition(x, y);
            }
        });
    }

    @Override
    public boolean withWidth(final int width) {
        return this.listConditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withWidth(width);
            }
        });
    }

    @Override
    public IntegerConditions withWidth() {
        return new IntegerListConditionsImpl(this.listConditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getWidth();
            }
        });
    }

    @Override
    public boolean withHeight(final int height) {
        return this.listConditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withHeight(height);
            }
        });
    }

    @Override
    public IntegerConditions withHeight() {
        return new IntegerListConditionsImpl(this.listConditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getHeight();
            }
        });
    }

    @Override
    public boolean withDimension(final int width, final int height) {
        return this.listConditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withDimension(width, height);
            }
        });
    }

    @Override
    public boolean withPositionAndDimension(final int x, final int y, final int width, final int height) {
        return this.listConditions.isVerified(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).withPositionAndDimension(x, y, width, height);
            }
        });
    }
}
