package org.fluentlenium.core.wait;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Conditions API for Fluent wait objects.
 *
 * @param <T> {@code this} object type to chain method calls
 */
public interface FluentWaitConditions<T> {

    /**
     * Get a conditions object used to wait for condition on given element.
     *
     * @param element element to wait for
     * @return conditions object
     */
    FluentConditions until(FluentWebElement element);

    /**
     * Get a conditions object used to wait for a condition on given elements.
     * <p>
     * At least one element must verify the condition to be verified.
     *
     * @param elements elements to wait for
     * @return conditions object
     */
    FluentListConditions until(List<? extends FluentWebElement> elements);

    /**
     * Get a conditions object used to wait for a condition on given elements.
     * <p>
     * Each element must verify the condition to be verified.
     *
     * @param elements elements to wait for
     * @return conditions object
     */
    FluentListConditions untilEach(List<? extends FluentWebElement> elements);

    /**
     * Get a conditions object used to wait for a condition on given element.
     *
     * @param element element to wait for
     * @return conditions object
     */
    FluentConditions untilElement(Supplier<? extends FluentWebElement> element);

    /**
     * Get a conditions object used to wait for a condition on given elements.
     * <p>
     * At least one element must verify the condition to be verified.
     *
     * @param elements elements to wait for
     * @return conditions object
     */
    FluentListConditions untilElements(Supplier<? extends List<? extends FluentWebElement>> elements);

    /**
     * Get a conditions object used to wait for a condition on given elements.
     * <p>
     * Each element must verify the condition to be verified.
     *
     * @param elements elements to wait for
     * @return conditions object
     */
    FluentListConditions untilEachElements(Supplier<? extends List<? extends FluentWebElement>> elements);

    /**
     * Get a condition object used to wait for a window condition.
     *
     * @param windowName name of the window to wait for
     * @return window conditions object
     */
    FluentWaitWindowConditions untilWindow(String windowName);

    /**
     * Get a condition object used to wait for a page condition.
     *
     * @return page conditions object
     */
    FluentWaitPageConditions untilPage();

    /**
     * Get a condition object used to wait for a page condition.
     *
     * @param page page to wait for
     * @return page conditions object
     */
    FluentWaitPageConditions untilPage(FluentPage page);

    /**
     * Waits unconditionally for an explicit amount of time.
     * <p>
     * The method should be used only as a last resort.
     * <p>
     * In most cases you should wait for some condition, e.g. visibility of particular element on the page.
     *
     * @param amount   amount of time
     * @param timeUnit unit of time
     * @return {@code this} object to chain method calls
     */
    T explicitlyFor(long amount, TimeUnit timeUnit);

    /**
     * Waits unconditionally for an explicit amount of time.
     * <p>
     * The method should be used only as a last resort.
     * <p>
     * In most cases you should wait for some condition, e.g. visibility of particular element on the page.
     *
     * @param amount amount of time to wait in milliseconds
     * @return {@code this} object to chain method calls
     */
    default T explicitlyFor(long amount) {
        return explicitlyFor(amount, TimeUnit.MILLISECONDS);
    }

    /**
     * waits until a {@link java.lang.Runnable} block execution ends without throwing an exception.
     *
     * The method is intended to be used with Java 8 Lambda expressions. This allows to use AssertJ/JUnit/TestNG
     * assertions to periodically check for conditions.
     *
     * <code>
     *     await().untilAsserted(() -&gt; assertThat(window().title()).isEqualTo("Fluentlenium"));
     * </code>
     *
     * @param block the code block that is responsible for executing the assertion and throwing AssertionError on failure.
     * @return {@code this} object to chain method calls
     */
    T untilAsserted(Runnable block);

}
