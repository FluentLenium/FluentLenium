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

    public FluentWaitElementList(FluentWait fluentControlWait, List<? extends FluentWebElement> elements) {
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

    public FluentWaitElementList atMost(long duration, TimeUnit unit) {
        fluentControlWait.atMost(duration, unit);
        return this;
    }

    public FluentWaitElementList atMost(long timeInMillis) {
        fluentControlWait.atMost(timeInMillis);
        return this;
    }

    public FluentWaitElementList pollingEvery(long duration, TimeUnit unit) {
        fluentControlWait.pollingEvery(duration, unit);
        return this;
    }

    public FluentWaitElementList pollingEvery(long duration) {
        fluentControlWait.pollingEvery(duration);
        return this;
    }

    public FluentWaitElementList ignoreAll(Collection<Class<? extends Throwable>> types) {
        fluentControlWait.ignoreAll(types);
        return this;
    }

    public FluentWaitElementList ignoring(Class<? extends RuntimeException> exceptionType) {
        fluentControlWait.ignoring(exceptionType);
        return this;
    }

    public FluentWaitElementList ignoring(Class<? extends RuntimeException> firstType,
            Class<? extends RuntimeException> secondType) {
        fluentControlWait.ignoring(firstType, secondType);
        return this;
    }

    public void untilPredicate(Predicate<FluentControl> predicate) {
        fluentControlWait.untilPredicate(predicate);
    }

    public FluentWaitElementList withMessage(String message) {
        fluentControlWait.withMessage(message);
        return this;
    }

    public FluentWaitElementList withMessage(Supplier<String> message) {
        fluentControlWait.withMessage(message);
        return this;
    }

    public FluentWaitElementList withNoDefaultsException() {
        fluentControlWait.withNoDefaultsException();
        return this;
    }

    public FluentConditions until(FluentWebElement element) {
        return fluentControlWait.until(element);
    }

    public FluentListConditions until(List<? extends FluentWebElement> elements) {
        return fluentControlWait.until(elements);
    }

    public FluentListConditions untilEach(List<? extends FluentWebElement> elements) {
        return fluentControlWait.untilEach(elements);
    }

    public FluentConditions untilElement(Supplier<? extends FluentWebElement> selector) {
        return fluentControlWait.untilElement(selector);
    }

    public FluentListConditions untilElements(Supplier<? extends List<? extends FluentWebElement>> selector) {
        return fluentControlWait.untilElements(selector);
    }

    public FluentListConditions untilEachElements(Supplier<? extends List<? extends FluentWebElement>> selector) {
        return fluentControlWait.untilEachElements(selector);
    }

    public FluentWaitWindowMatcher untilWindow(String windowName) {
        return fluentControlWait.untilWindow(windowName);
    }

    public FluentWaitPageMatcher untilPage() {
        return fluentControlWait.untilPage();
    }

    public FluentWaitPageMatcher untilPage(FluentPage page) {
        return fluentControlWait.untilPage(page);
    }

    public FluentWaitElementList explicitlyFor(long amount, TimeUnit timeUnit) {
        fluentControlWait.explicitlyFor(amount, timeUnit);
        return this;
    }

    public FluentWaitElementList explicitlyFor(long amount) {
        fluentControlWait.explicitlyFor(amount);
        return this;
    }

    public void until(Supplier<Boolean> isTrue) {
        fluentControlWait.until(isTrue);
    }

    public <T> T until(Function<? super FluentControl, T> isTrue) {
        return fluentControlWait.until(isTrue);
    }

    public boolean useCustomMessage() {
        return fluentControlWait.useCustomMessage();
    }
}
