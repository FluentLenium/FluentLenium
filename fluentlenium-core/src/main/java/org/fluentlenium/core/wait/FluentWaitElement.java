package org.fluentlenium.core.wait;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * A wait object wrapping default selenium {@link org.openqa.selenium.support.ui.FluentWait} object into a more
 * complete API, allowing to wait for any condition to be verified on an underlying element.
 */
public class FluentWaitElement implements FluentWaitFunctional<FluentControl>, FluentWaitConditions<FluentWaitElement>,
        FluentWaitConfiguration<FluentWaitElement> {
    private final FluentWebElement element;

    private final FluentWait controlWait;

    /**
     * Creates a new fluent wait for a given element.
     *
     * @param controlWait underlying wait from control interface
     * @param element     underlying element
     */
    public FluentWaitElement(FluentWait controlWait, FluentWebElement element) {
        this.controlWait = controlWait;
        this.element = element;
    }

    /**
     * Get a conditions object used to wait for condition on current element.
     *
     * @return conditions object
     */
    public FluentConditions until() {
        return controlWait.until(element);
    }

    @Override
    public org.openqa.selenium.support.ui.FluentWait getWait() {
        return controlWait.getWait();
    }

    @Override
    public FluentWaitElement atMost(Duration duration) {
        controlWait.atMost(duration);
        return this;
    }

    @Override
    public FluentWaitElement atMost(long duration, TimeUnit unit) {
        controlWait.atMost(duration, unit);
        return this;
    }

    @Override
    public FluentWaitElement atMost(long timeInMillis) {
        controlWait.atMost(timeInMillis);
        return this;
    }

    @Override
    public FluentWaitElement pollingEvery(Duration duration) {
        controlWait.pollingEvery(duration);
        return this;
    }

    @Override
    public FluentWaitElement pollingEvery(long duration, TimeUnit unit) {
        controlWait.pollingEvery(duration, unit);
        return this;
    }

    @Override
    public FluentWaitElement pollingEvery(long duration) {
        controlWait.pollingEvery(duration);
        return this;
    }

    @Override
    public FluentWaitElement ignoreAll(Collection<Class<? extends Throwable>> types) {
        controlWait.ignoreAll(types);
        return this;
    }

    @Override
    public FluentWaitElement ignoring(Class<? extends RuntimeException> exceptionType) {
        controlWait.ignoring(exceptionType);
        return this;
    }

    @Override
    public FluentWaitElement ignoring(Class<? extends RuntimeException> firstType, Class<? extends RuntimeException> secondType) {
        controlWait.ignoring(firstType, secondType);
        return this;
    }

    @Override
    public FluentWaitElement withMessage(String message) {
        controlWait.withMessage(message);
        return this;
    }

    @Override
    public FluentWaitElement withMessage(Supplier<String> message) {
        controlWait.withMessage(message);
        return this;
    }

    @Override
    public boolean hasMessageDefined() {
        return controlWait.hasMessageDefined();
    }

    @Override
    public FluentWaitElement withNoDefaultsException() {
        controlWait.withNoDefaultsException();
        return this;
    }

    @Override
    public void untilPredicate(Predicate<FluentControl> predicate) {
        controlWait.untilPredicate(predicate);
    }

    @Override
    public void until(Supplier<Boolean> isTrue) {
        controlWait.until(isTrue);
    }

    @Override
    public <T> T until(Function<? super FluentControl, T> isTrue) {
        return controlWait.until(isTrue);
    }

    @Override
    public FluentConditions until(FluentWebElement element) {
        return controlWait.until(element);
    }

    @Override
    public FluentListConditions until(List<? extends FluentWebElement> elements) {
        return controlWait.until(elements);
    }

    @Override
    public FluentListConditions untilEach(List<? extends FluentWebElement> elements) {
        return controlWait.untilEach(elements);
    }

    @Override
    public FluentConditions untilElement(Supplier<? extends FluentWebElement> selector) {
        return controlWait.untilElement(selector);
    }

    @Override
    public FluentListConditions untilElements(Supplier<? extends List<? extends FluentWebElement>> selector) {
        return controlWait.untilElements(selector);
    }

    @Override
    public FluentListConditions untilEachElements(Supplier<? extends List<? extends FluentWebElement>> selector) {
        return controlWait.untilEachElements(selector);
    }

    @Override
    public FluentWaitWindowConditions untilWindow(String windowName) {
        return controlWait.untilWindow(windowName);
    }

    @Override
    public FluentWaitPageConditions untilPage() {
        return controlWait.untilPage();
    }

    @Override
    public FluentWaitPageConditions untilPage(FluentPage page) {
        return controlWait.untilPage(page);
    }

    @Override
    public FluentWaitElement explicitlyFor(long amount) {
        controlWait.explicitlyFor(amount);
        return this;
    }

    @Override
    public FluentWaitElement explicitlyFor(long amount, TimeUnit timeUnit) {
        controlWait.explicitlyFor(amount, timeUnit);
        return this;
    }

    /**
     * Wait until function returns true
     *
     * @param function function to be performed
     * @param <T> FluentWaitElement
     * @return FluentWaitElement
     */
    @Deprecated
    public <T> T until(com.google.common.base.Function<? super FluentControl, T> function) {
        return controlWait.until(function);
    }
}
