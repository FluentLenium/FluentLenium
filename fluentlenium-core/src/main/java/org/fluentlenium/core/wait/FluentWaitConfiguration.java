package org.fluentlenium.core.wait;

import com.google.common.base.Supplier;

import java.util.concurrent.TimeUnit;

/**
 * Configuration API of fluent wait object.
 *
 * @param <T> {@code this} object type to chain method calls
 */
public interface FluentWaitConfiguration<T> {

    /**
     * Get the underlying selenium wait object
     *
     * @return selenium wait
     */
    org.openqa.selenium.support.ui.FluentWait getWait();

    /**
     * Configure timeout for this wait object.
     *
     * @param duration duration
     * @param unit     time unit
     * @return {@code this} object to chain method calls
     */
    T atMost(long duration, TimeUnit unit);

    /**
     * Configure wait timeout for this wait object.
     *
     * @param duration duration in millisecond
     * @return {@code this} object to chain method calls
     */
    T atMost(long duration);

    /**
     * Configure polling time for this wait object.
     *
     * @param duration duration between each condition invocation
     * @param unit     time unit
     * @return {@code this} object to chain method calls
     */
    T pollingEvery(long duration, TimeUnit unit);

    /**
     * Configure polling time for this wait object.
     *
     * @param duration duration in millisecond between each condition invocation
     * @return {@code this} object to chain method calls
     */
    T pollingEvery(long duration);

    /**
     * Add given exceptions to ignore list to avoid breaking the wait when they occurs in the underlying condition evaluation.
     *
     * @param types collection of exception type to ignore
     * @return {@code this} object to chain method calls
     */
    T ignoreAll(java.util.Collection<java.lang.Class<? extends Throwable>> types);

    /**
     * Add given exception to ignore list to avoid breaking the wait when they occurs in the underlying condition evaluation.
     *
     * @param exceptionType exception type to ignore
     * @return {@code this} object to chain method calls
     */
    T ignoring(java.lang.Class<? extends java.lang.RuntimeException> exceptionType);

    /**
     * Add given exceptions to ignore list to avoid breaking the wait when they occurs in the underlying condition evaluation.
     *
     * @param firstType  exception type to ignore
     * @param secondType exception type to ignore
     * @return {@code this} object to chain method calls
     */
    T ignoring(java.lang.Class<? extends java.lang.RuntimeException> firstType,
            java.lang.Class<? extends java.lang.RuntimeException> secondType);

    /**
     * Configures a custom message to be used if the condition fails during the timeout duration.
     *
     * @param message failing message
     * @return {@code this} object to chain method calls
     */
    T withMessage(String message);

    /**
     * Configures a custom message supplier to be used if the condition fails during the timeout duration.
     *
     * @param message failing message
     * @return {@code this} object to chain method calls
     */
    T withMessage(Supplier<String> message);

    /**
     * Check if a message is defined.
     *
     * @return true if this fluent wait use a custom message, false otherwise
     */
    boolean hasMessageDefined();

    /**
     * Removes default exceptions from exceptions ignore list.
     *
     * @return {@code this} object to chain method calls
     */
    T withNoDefaultsException();
}
