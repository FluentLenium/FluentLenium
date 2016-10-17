package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.message.MessageProxy;

public class RectangleConditionsAssert extends AbstractAssert<RectangleConditionsAssert, RectangleConditions> {
    public RectangleConditionsAssert(final RectangleConditions rectangle) {
        super(rectangle, RectangleConditionsAssert.class);
    }

    public RectangleConditionsAssert withX(final int x) {
        final boolean verify = actual.x(x);
        if (!verify) {
            failWithMessage(MessageProxy.message(actual));
        }
        return this;
    }
}
