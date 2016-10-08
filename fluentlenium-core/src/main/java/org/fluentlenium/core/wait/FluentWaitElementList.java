package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A wait object wrapping default selenium {@link org.openqa.selenium.support.ui.FluentWait} object into a more
 * complete API, allowing to wait for any condition to be verified on underlying elements.
 */
public class FluentWaitElementList implements FluentWaitFunctional<FluentControl>, FluentWaitConditions<FluentWaitElementList>,
        FluentWaitConfiguration<FluentWaitElementList> {
    private final List<? extends FluentWebElement> elements;

    private final FluentWait controlWait;

    /**
     * Creates a new fluent wait for a given elements.
     *
     * @param controlWait underlying wait from control interface
     * @param elements    underlying elements
     */
    public FluentWaitElementList(final FluentWait controlWait, final List<? extends FluentWebElement> elements) {
        this.controlWait = controlWait;
        this.elements = elements;
    }

    /**
     * Get a conditions object used to wait for condition on current elements.
     * <p>
     * At least one element must verify the condition to be verified.
     *
     * @return conditions object
     */
    public FluentConditions until() {
        return controlWait.until(elements);
    }

    /**
     * Get a conditions object used to wait for condition on current elements.
     * <p>
     * Each element must verify the condition to be verified.
     *
     * @return conditions object
     */
    public FluentConditions untilEach() {
        return controlWait.untilEach(elements);
    }

    @Override
    public org.openqa.selenium.support.ui.FluentWait getWait() {
        return controlWait.getWait();
    }

    @Override
    public FluentWaitElementList atMost(final long duration, final TimeUnit unit) {
        controlWait.atMost(duration, unit);
        return this;
    }

    @Override
    public FluentWaitElementList atMost(final long timeInMillis) {
        controlWait.atMost(timeInMillis);
        return this;
    }

    @Override
    public FluentWaitElementList pollingEvery(final long duration, final TimeUnit unit) {
        controlWait.pollingEvery(duration, unit);
        return this;
    }

    @Override
    public FluentWaitElementList pollingEvery(final long duration) {
        controlWait.pollingEvery(duration);
        return this;
    }

    @Override
    public FluentWaitElementList ignoreAll(final Collection<Class<? extends Throwable>> types) {
        controlWait.ignoreAll(types);
        return this;
    }

    @Override
    public FluentWaitElementList ignoring(final Class<? extends RuntimeException> exceptionType) {
        controlWait.ignoring(exceptionType);
        return this;
    }

    @Override
    public FluentWaitElementList ignoring(final Class<? extends RuntimeException> firstType,
            final Class<? extends RuntimeException> secondType) {
        controlWait.ignoring(firstType, secondType);
        return this;
    }

    @Override
    public void untilPredicate(final Predicate<FluentControl> predicate) {
        controlWait.untilPredicate(predicate);
    }

    @Override
    public void until(final Supplier<Boolean> supplier) {
        controlWait.until(supplier);
    }

    @Override
    public <T> T until(final Function<? super FluentControl, T> function) {
        return controlWait.until(function);
    }

    @Override
    public FluentWaitElementList withMessage(final String message) {
        controlWait.withMessage(message);
        return this;
    }

    @Override
    public FluentWaitElementList withMessage(final Supplier<String> message) {
        controlWait.withMessage(message);
        return this;
    }

    @Override
    public boolean hasMessageDefined() {
        return controlWait.hasMessageDefined();
    }

    @Override
    public FluentWaitElementList withNoDefaultsException() {
        controlWait.withNoDefaultsException();
        return this;
    }

    @Override
    public FluentConditions until(final FluentWebElement element) {
        return controlWait.until(element);
    }

    @Override
    public FluentListConditions until(final List<? extends FluentWebElement> elements) {
        return controlWait.until(elements);
    }

    @Override
    public FluentListConditions untilEach(final List<? extends FluentWebElement> elements) {
        return controlWait.untilEach(elements);
    }

    @Override
    public FluentConditions untilElement(final Supplier<? extends FluentWebElement> selector) {
        return controlWait.untilElement(selector);
    }

    @Override
    public FluentListConditions untilElements(final Supplier<? extends List<? extends FluentWebElement>> selector) {
        return controlWait.untilElements(selector);
    }

    @Override
    public FluentListConditions untilEachElements(final Supplier<? extends List<? extends FluentWebElement>> selector) {
        return controlWait.untilEachElements(selector);
    }

    @Override
    public FluentWaitWindowConditions untilWindow(final String windowName) {
        return controlWait.untilWindow(windowName);
    }

    @Override
    public FluentWaitPageConditions untilPage() {
        return controlWait.untilPage();
    }

    @Override
    public FluentWaitPageConditions untilPage(final FluentPage page) {
        return controlWait.untilPage(page);
    }

    @Override
    public FluentWaitElementList explicitlyFor(final long amount, final TimeUnit timeUnit) {
        controlWait.explicitlyFor(amount, timeUnit);
        return this;
    }

    @Override
    public FluentWaitElementList explicitlyFor(final long amount) {
        controlWait.explicitlyFor(amount);
        return this;
    }
}
