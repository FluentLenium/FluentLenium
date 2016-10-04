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

public class FluentWaitElementList {
    private final List<? extends FluentWebElement> elements;

    private final FluentWait fluentControlWait;

    public FluentWaitElementList(final FluentWait fluentControlWait, final List<? extends FluentWebElement> elements) {
        this.fluentControlWait = fluentControlWait;
        this.elements = elements;
    }

    public FluentConditions until() {
        return fluentControlWait.until(elements);
    }

    public FluentConditions untilEach() {
        return fluentControlWait.untilEach(elements);
    }

    public org.openqa.selenium.support.ui.FluentWait getWait() {
        return fluentControlWait.getWait();
    }

    public FluentWaitElementList atMost(final long duration, final TimeUnit unit) {
        fluentControlWait.atMost(duration, unit);
        return this;
    }

    public FluentWaitElementList atMost(final long timeInMillis) {
        fluentControlWait.atMost(timeInMillis);
        return this;
    }

    public FluentWaitElementList pollingEvery(final long duration, final TimeUnit unit) {
        fluentControlWait.pollingEvery(duration, unit);
        return this;
    }

    public FluentWaitElementList pollingEvery(final long duration) {
        fluentControlWait.pollingEvery(duration);
        return this;
    }

    public FluentWaitElementList ignoreAll(final Collection<Class<? extends Throwable>> types) {
        fluentControlWait.ignoreAll(types);
        return this;
    }

    public FluentWaitElementList ignoring(final Class<? extends RuntimeException> exceptionType) {
        fluentControlWait.ignoring(exceptionType);
        return this;
    }

    public FluentWaitElementList ignoring(final Class<? extends RuntimeException> firstType,
            final Class<? extends RuntimeException> secondType) {
        fluentControlWait.ignoring(firstType, secondType);
        return this;
    }

    public void untilPredicate(final Predicate<FluentControl> predicate) {
        fluentControlWait.untilPredicate(predicate);
    }

    public FluentWaitElementList withMessage(final String message) {
        fluentControlWait.withMessage(message);
        return this;
    }

    public FluentWaitElementList withMessage(final Supplier<String> message) {
        fluentControlWait.withMessage(message);
        return this;
    }

    public FluentWaitElementList withNoDefaultsException() {
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

    public FluentWaitElementList explicitlyFor(final long amount, final TimeUnit timeUnit) {
        fluentControlWait.explicitlyFor(amount, timeUnit);
        return this;
    }

    public FluentWaitElementList explicitlyFor(final long amount) {
        fluentControlWait.explicitlyFor(amount);
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
