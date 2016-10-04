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

public class FluentWaitElement {
    private final FluentWebElement element;

    private final FluentWait fluentControlWait;

    public FluentWaitElement(final FluentWait fluentControlWait, final FluentWebElement element) {
        this.fluentControlWait = fluentControlWait;
        this.element = element;
    }

    /**
     * Return a matcher configured to wait for particular condition for given element.
     *
     * @return fluent wait matcher
     */
    public FluentConditions until() {
        return this.fluentControlWait.until(element);
    }

    public org.openqa.selenium.support.ui.FluentWait getWait() {
        return fluentControlWait.getWait();
    }

    public FluentWaitElement atMost(final long duration, final TimeUnit unit) {
        fluentControlWait.atMost(duration, unit);
        return this;
    }

    public FluentWaitElement atMost(final long timeInMillis) {
        fluentControlWait.atMost(timeInMillis);
        return this;
    }

    public FluentWaitElement pollingEvery(final long duration, final TimeUnit unit) {
        fluentControlWait.pollingEvery(duration, unit);
        return this;
    }

    public FluentWaitElement pollingEvery(final long duration) {
        fluentControlWait.pollingEvery(duration);
        return this;
    }

    public FluentWaitElement ignoreAll(final Collection<Class<? extends Throwable>> types) {
        fluentControlWait.ignoreAll(types);
        return this;
    }

    public FluentWaitElement ignoring(final Class<? extends RuntimeException> exceptionType) {
        fluentControlWait.ignoring(exceptionType);
        return this;
    }

    public FluentWaitElement ignoring(final Class<? extends RuntimeException> firstType,
            final Class<? extends RuntimeException> secondType) {
        fluentControlWait.ignoring(firstType, secondType);
        return this;
    }

    public void untilPredicate(final Predicate<FluentControl> predicate) {
        fluentControlWait.untilPredicate(predicate);
    }

    public FluentWaitElement withMessage(final String message) {
        fluentControlWait.withMessage(message);
        return this;
    }

    public FluentWaitElement withMessage(final Supplier<String> message) {
        fluentControlWait.withMessage(message);
        return this;
    }

    public FluentWaitElement withNoDefaultsException() {
        fluentControlWait.withNoDefaultsException();
        return this;
    }

    public FluentConditions until(final FluentWebElement element) {
        return fluentControlWait.until(element);
    }

    public FluentListConditions until(final List<? extends FluentWebElement> elements) {
        return fluentControlWait.until(elements);
    }

    public FluentListConditions untilEach(final List<? extends FluentWebElement> elements) {
        return fluentControlWait.untilEach(elements);
    }

    public FluentConditions untilElement(final Supplier<? extends FluentWebElement> selector) {
        return fluentControlWait.untilElement(selector);
    }

    public FluentListConditions untilElements(final Supplier<? extends List<? extends FluentWebElement>> selector) {
        return fluentControlWait.untilElements(selector);
    }

    public FluentListConditions untilEachElements(final Supplier<? extends List<? extends FluentWebElement>> selector) {
        return fluentControlWait.untilEachElements(selector);
    }

    public FluentWaitWindowMatcher untilWindow(final String windowName) {
        return fluentControlWait.untilWindow(windowName);
    }

    public FluentWaitPageMatcher untilPage() {
        return fluentControlWait.untilPage();
    }

    public FluentWaitPageMatcher untilPage(final FluentPage page) {
        return fluentControlWait.untilPage(page);
    }

    public FluentWaitElement explicitlyFor(final long amount) {
        fluentControlWait.explicitlyFor(amount);
        return this;
    }

    public FluentWaitElement explicitlyFor(final long amount, final TimeUnit timeUnit) {
        fluentControlWait.explicitlyFor(amount, timeUnit);
        return this;
    }

    public void until(final Supplier<Boolean> isTrue) {
        fluentControlWait.until(isTrue);
    }

    public <T> T until(final Function<? super FluentControl, T> isTrue) {
        return fluentControlWait.until(isTrue);
    }

    public boolean useCustomMessage() {
        return fluentControlWait.useCustomMessage();
    }

}
