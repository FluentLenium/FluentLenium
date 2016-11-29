package org.fluentlenium.assertj.custom;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.IntegerAssert;
import org.fluentlenium.core.conditions.ConditionsObject;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.message.MessageProxy;
import org.openqa.selenium.Rectangle;

public class RectangleConditionsAssert extends AbstractAssert<RectangleConditionsAssert, RectangleConditions> {
    private final Supplier<RectangleConditions> messageSupplier;

    public RectangleConditionsAssert(final RectangleConditions rectangle, final Supplier<RectangleConditions> messageSupplier) {
        super(rectangle, RectangleConditionsAssert.class);
        this.messageSupplier = messageSupplier;
    }

    public RectangleConditionsAssert verifies(final Function<RectangleConditions, Boolean> condition) {
        final boolean verify = condition.apply(actual);
        if (!verify) {
            final RectangleConditions message = messageSupplier.get();
            condition.apply(message);
            failWithMessage(MessageProxy.message(message));
        }
        return this;
    }

    public RectangleConditionsAssert withX(final int x) {
        return verifies(new Function<RectangleConditions, Boolean>() {
            @Override
            public Boolean apply(final RectangleConditions input) {
                return input.x(x);
            }
        });
    }

    public IntegerAssert withX() {
        final Rectangle rectangle = (Rectangle) ((ConditionsObject) actual).getActualObject();
        final RectangleConditions message = messageSupplier.get();
        message.x();
        return new IntegerAssert(rectangle.getX()).as(MessageProxy.message(message));
    }
}
