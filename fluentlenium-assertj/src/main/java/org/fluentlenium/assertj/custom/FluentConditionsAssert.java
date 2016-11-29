package org.fluentlenium.assertj.custom;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.conditions.ConditionsObject;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.message.MessageProxy;

/**
 * Assertions on fluent conditions
 */
public class FluentConditionsAssert<S extends FluentConditionsAssert<S, A>, A extends FluentConditions>
        extends AbstractAssert<S, A> {

    public FluentConditionsAssert(final A actual, final Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * Get the message context for actual object.
     *
     * @return actual message context.
     */
    protected String getActualMessageContext() {
        if (actual instanceof ConditionsObject) {
            return String.valueOf(((ConditionsObject) actual).getActualObject());
        } else {
            return String.valueOf(actual);
        }
    }

    /**
     * Verifies that the actual object verifies the given condition function.
     *
     * @param condition condition function
     * @return {@code this} assertion object
     * @throws AssertionError if the actual value does not verify the condition
     */
    public FluentConditionsAssert verifies(final Function<FluentConditions, Boolean> condition) {
        final String actualContext = getActualMessageContext();
        final FluentConditions actualProxy = MessageProxy.wrap(FluentConditions.class, actual, actualContext);
        final boolean verify = condition.apply(actualProxy);
        if (!verify) {
            failWithMessage(MessageProxy.message(actualProxy));
        }
        return this;
    }

    /**
     * Verifies that the actual object is present
     *
     * @return {@code this} assertion object
     * @throws AssertionError if the actual value is not present
     */
    public FluentConditionsAssert isPresent() {
        return verifies(new Function<FluentConditions, Boolean>() {
            @Override
            public Boolean apply(final FluentConditions input) {
                return input.present();
            }
        });
    }

    /**
     * Verifies that the actual object is not present
     *
     * @return {@code this} assertion object
     * @throws AssertionError if the actual value is present
     */
    public FluentConditionsAssert isNotPresent() {
        return verifies(new Function<FluentConditions, Boolean>() {
            @Override
            public Boolean apply(final FluentConditions input) {
                return input.not().present();
            }
        });
    }

    /**
     * Perform assertions on rectangle of actual object
     *
     * @return rectangle assertion object
     */
    public RectangleConditionsAssert hasRectangle() {
        final String actualContext = getActualMessageContext();

        return new RectangleConditionsAssert(actual.rectangle(), new Supplier<RectangleConditions>() {
            @Override
            public RectangleConditions get() {
                final FluentConditions actualProxy = MessageProxy.wrap(FluentConditions.class, actual, actualContext);
                final RectangleConditions messageBuilder = actualProxy.rectangle();
                return messageBuilder;
            }
        });
    }
}
