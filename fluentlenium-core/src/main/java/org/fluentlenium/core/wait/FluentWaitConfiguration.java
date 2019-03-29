package org.fluentlenium.core.wait;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

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
     * @return {@code this} object to chain method calls
     */
    T atMost(Duration duration);

    /**
     * Configure timeout for this wait object.
     *
     * @param duration duration
     * @param unit     time unit
     * @return {@code this} object to chain method calls
     */
    default T atMost(long duration, TimeUnit unit) {
        return atMost(Duration.of(duration, TimeToChronoUnitConverter.of(unit)));
    }

    /**
     * Configure wait timeout for this wait object.
     *
     * @param duration duration in millisecond
     * @return {@code this} object to chain method calls
     */
    default T atMost(long duration) {
        return atMost(Duration.ofMillis(duration));
    }

    /**
     * Configure polling time for this wait object.
     *
     * @param duration duration between each condition invocation
     * @return {@code this} object to chain method calls
     */
    T pollingEvery(Duration duration);

    /**
     * Configure polling time for this wait object.
     *
     * @param duration duration between each condition invocation
     * @param unit     time unit
     * @return {@code this} object to chain method calls
     */
    default T pollingEvery(long duration, TimeUnit unit) {
        return pollingEvery(Duration.of(duration, TimeToChronoUnitConverter.of(unit)));
    }

    /**
     * Configure polling time for this wait object.
     *
     * @param duration duration in millisecond between each condition invocation
     * @return {@code this} object to chain method calls
     */
    default T pollingEvery(long duration) {
        return pollingEvery(Duration.ofMillis(duration));
    }

    /**
     * Add given exceptions to ignore list to avoid breaking the wait when they occurs in the underlying condition evaluation.
     *
     * @param types collection of exception type to ignore
     * @return {@code this} object to chain method calls
     */
    T ignoreAll(Collection<Class<? extends Throwable>> types);

    /**
     * Add given exception to ignore list to avoid breaking the wait when they occurs in the underlying condition evaluation.
     *
     * @param exceptionType exception type to ignore
     * @return {@code this} object to chain method calls
     */
    T ignoring(Class<? extends RuntimeException> exceptionType);

    /**
     * Add given exceptions to ignore list to avoid breaking the wait when they occurs in the underlying condition evaluation.
     *
     * @param firstType  exception type to ignore
     * @param secondType exception type to ignore
     * @return {@code this} object to chain method calls
     */
    T ignoring(Class<? extends RuntimeException> firstType, Class<? extends RuntimeException> secondType);

    /**
     * Configures a custom message to be used if the condition fails during the timeout duration.
     *
     * @param message failing message
     * @return {@code this} object to chain method calls
     */
    default T withMessage(String message) {
        return withMessage(() -> message);
    }

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
