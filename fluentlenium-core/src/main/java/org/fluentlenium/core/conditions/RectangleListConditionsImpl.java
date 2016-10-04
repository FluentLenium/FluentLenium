package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Rectangle;

/**
 * Conditions implement for rectangles of list of elements.
 */
public class RectangleListConditionsImpl extends AbstractObjectListConditions<Rectangle, RectangleConditions>
        implements RectangleConditions {
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
                return input.conditions().rectangle();
            }
        });
    }

    @Override
    public RectangleListConditionsImpl not() {
        return new RectangleListConditionsImpl(this.conditions.not(), objectGetter, conditionsGetter);
    }

    @Override
    public boolean x(final int x) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).x(x);
            }
        });
    }

    @Override
    public boolean y(final int y) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).y(y);
            }
        });
    }

    @Override
    public IntegerConditions x() {
        return new IntegerListConditionsImpl(this.conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getX();
            }
        });
    }

    @Override
    public IntegerConditions y() {
        return new IntegerListConditionsImpl(this.conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getY();
            }
        });
    }

    @Override
    public boolean position(final int x, final int y) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).position(x, y);
            }
        });
    }

    @Override
    public boolean width(final int width) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).width(width);
            }
        });
    }

    @Override
    public IntegerConditions width() {
        return new IntegerListConditionsImpl(this.conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getWidth();
            }
        });
    }

    @Override
    public boolean height(final int height) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).height(height);
            }
        });
    }

    @Override
    public IntegerConditions height() {
        return new IntegerListConditionsImpl(this.conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return input.getElement().getRect().getHeight();
            }
        });
    }

    @Override
    public boolean dimension(final int width, final int height) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).dimension(width, height);
            }
        });
    }

    @Override
    public boolean positionAndDimension(final int x, final int y, final int width, final int height) {
        return this.conditions.verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return conditionsGetter.apply(input).positionAndDimension(x, y, width, height);
            }
        });
    }
}
