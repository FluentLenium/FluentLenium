package org.fluentlenium.assertj;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;

/**
 * Utility methods for working with assertions.
 */
public final class AssertionTestSupport {

    private AssertionTestSupport() {
        //Utility class
    }

    /**
     * Asserts that the argument callable throws an {@link AssertionError}.
     *
     * @param shouldRaiseThrowable the callable
     * @return a throwable assert
     */
    public static AbstractThrowableAssert<?, ? extends Throwable> assertThatAssertionErrorIsThrownBy(ThrowingCallable shouldRaiseThrowable) {
        return Assertions.assertThatThrownBy(shouldRaiseThrowable).isInstanceOf(AssertionError.class);
    }
}
