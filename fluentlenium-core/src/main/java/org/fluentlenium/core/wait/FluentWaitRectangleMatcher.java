package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.conditions.IntegerConditions;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.openqa.selenium.Rectangle;

import static org.fluentlenium.core.wait.FluentWaitMessages.equalToMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotPositionAndDimensionMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNotPositionMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasPositionAndDimensionMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasPositionMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateNotVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPredicateVerifiedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.notEqualToMessage;


public class FluentWaitRectangleMatcher implements RectangleConditions {
    private final AbstractWaitElementMatcher matcher;

    public FluentWaitRectangleMatcher(AbstractWaitElementMatcher matcher) {
        this.matcher = matcher;
    }

    protected RectangleConditions hasRectangle() {
        RectangleConditions conditions = matcher.find().each().hasRectangle();
        if (matcher.negation) {
            conditions = conditions.not();
        }
        return conditions;
    }

    @Override
    public boolean isVerified(final Predicate<Rectangle> predicate) {
        matcher.until(matcher.wait, new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                return hasRectangle().isVerified(predicate);
            }
        }, matcher.negation ? isPredicateNotVerifiedMessage(matcher.selectionName) : isPredicateVerifiedMessage(matcher.selectionName));
        return true;
    }

    @Override
    public FluentWaitRectangleMatcher not() {
        return new FluentWaitRectangleMatcher((AbstractWaitElementMatcher) matcher.not());
    }

    @Override
    public boolean withX(final int x) {
        matcher.until(matcher.wait, new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                return hasRectangle().withX(x);
            }
        }, matcher.negation ? notEqualToMessage(matcher.selectionName, x) : equalToMessage(matcher.selectionName, x));
        return true;
    }

    @Override
    public IntegerConditions withX() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return hasRectangle().withX();
            }
        });
    }

    @Override
    public boolean withY(final int y) {
        matcher.until(matcher.wait, new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                return hasRectangle().withY(y);
            }
        }, matcher.negation ? notEqualToMessage(matcher.selectionName, y) : equalToMessage(matcher.selectionName, y));
        return true;
    }

    @Override
    public IntegerConditions withY() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return hasRectangle().withY();
            }
        });
    }

    @Override
    public boolean withPosition(final int x, final int y) {
        matcher.until(matcher.wait, new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                return hasRectangle().withPosition(x, y);
            }
        }, matcher.negation ? hasNotPositionMessage(matcher.selectionName, x, y) : hasPositionMessage(matcher.selectionName, x, y));
        return true;
    }

    @Override
    public boolean withWidth(final int width) {
        matcher.until(matcher.wait, new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                return hasRectangle().withWidth(width);
            }
        }, matcher.negation ? notEqualToMessage(matcher.selectionName, width) : equalToMessage(matcher.selectionName, width));
        return true;
    }

    @Override
    public IntegerConditions withWidth() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return hasRectangle().withWidth();
            }
        });
    }

    @Override
    public boolean withHeight(final int height) {
        matcher.until(matcher.wait, new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                return hasRectangle().withHeight(height);
            }
        }, matcher.negation ? notEqualToMessage(matcher.selectionName, height) : equalToMessage(matcher.selectionName, height));
        return true;
    }

    @Override
    public IntegerConditions withHeight() {
        return new FluentWaitIntegerMatcher(matcher, new Supplier<IntegerConditions>() {
            @Override
            public IntegerConditions get() {
                return hasRectangle().withHeight();
            }
        });
    }

    @Override
    public boolean withDimension(final int width, final int height) {
        matcher.until(matcher.wait, new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                return hasRectangle().withPosition(width, height);
            }
        }, matcher.negation ? FluentWaitMessages.hasNotDimensionMessage(matcher.selectionName, width, height) : FluentWaitMessages.hasDimensionMessage(matcher.selectionName, width, height));
        return true;
    }

    @Override
    public boolean withPositionAndDimension(final int x, final int y, final int width, final int height) {
        matcher.until(matcher.wait, new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                return hasRectangle().withPositionAndDimension(x, y, width, height);
            }
        }, matcher.negation ? hasNotPositionAndDimensionMessage(matcher.selectionName, x, y, width, height) : hasPositionAndDimensionMessage(matcher.selectionName, x, y, width, height));
        return true;
    }
}
