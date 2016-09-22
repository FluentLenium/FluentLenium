package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.fluentlenium.core.conditions.IntegerConditions;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.openqa.selenium.Rectangle;


public class FluentWaitRectangleMatcher extends AbstractFluentWaitConditionsMatcher<Rectangle, RectangleConditions> implements RectangleConditions {
    protected FluentWaitRectangleMatcher(AbstractWaitElementMatcher matcher, Supplier<RectangleConditions> conditionsSupplier, Supplier<RectangleConditions> messageBuilderSupplier) {
        super(matcher, conditionsSupplier, messageBuilderSupplier);
    }

    @Override
    public FluentWaitRectangleMatcher not() {
        return new FluentWaitRectangleMatcher((AbstractWaitElementMatcher) matcher.not(), conditionsSupplier, messageBuilderSupplier);
    }

    @Override
    public boolean withX(final int x) {
        return withX().equalTo(x);
    }

    @Override
    public IntegerConditions withX() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return conditions(true).withX();
            }
        }, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return messageBuilder(true).withX();
            }
        });
    }

    @Override
    public boolean withY(final int y) {
        return withY().equalTo(y);
    }

    @Override
    public IntegerConditions withY() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return conditions(true).withY();
            }
        }, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return messageBuilder(true).withY();
            }
        });
    }

    @Override
    public boolean withPosition(final int x, final int y) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<RectangleConditions, Boolean>() {
            @Override
            public Boolean apply(RectangleConditions input) {
                return input.withPosition(x, y);
            }
        });
        return true;
    }

    @Override
    public boolean withWidth(final int width) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<RectangleConditions, Boolean>() {
            @Override
            public Boolean apply(RectangleConditions input) {
                return input.withWidth(width);
            }
        });
        return true;
    }

    @Override
    public IntegerConditions withWidth() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return conditions(true).withWidth();
            }
        }, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return messageBuilder(true).withWidth();
            }
        });
    }

    @Override
    public boolean withHeight(final int height) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<RectangleConditions, Boolean>() {
            @Override
            public Boolean apply(RectangleConditions input) {
                return input.withHeight(height);
            }
        });
        return true;
    }

    @Override
    public IntegerConditions withHeight() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return conditions(true).withHeight();
            }
        }, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return messageBuilder(true).withHeight();
            }
        });
    }

    @Override
    public boolean withDimension(final int width, final int height) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<RectangleConditions, Boolean>() {
            @Override
            public Boolean apply(RectangleConditions input) {
                return input.withDimension(width, height);
            }
        });
        return true;
    }

    @Override
    public boolean withPositionAndDimension(final int x, final int y, final int width, final int height) {
        matcher.until(matcher.wait, conditions(), messageBuilder(), new Function<RectangleConditions, Boolean>() {
            @Override
            public Boolean apply(RectangleConditions input) {
                return input.withPositionAndDimension(x, y, width, height);
            }
        });
        return true;
    }
}
