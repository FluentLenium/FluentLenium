package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.wait.WaitConditionProxy;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A Fluent wait object.
 */
public class FluentWait implements org.openqa.selenium.support.ui.Wait<FluentControl> {

    private final org.openqa.selenium.support.ui.FluentWait<FluentControl> wait;
    private final WebDriver driver;
    private boolean useDefaultException;
    private boolean useCustomMessage;

    public org.openqa.selenium.support.ui.FluentWait getWait() {
        return wait;
    }

    public FluentWait(FluentControl fluentControl) {
        wait = new org.openqa.selenium.support.ui.FluentWait<>(fluentControl);
        driver = fluentControl.getDriver();
        useDefaultException = true;
    }

    public FluentWait atMost(long duration, TimeUnit unit) {
        wait.withTimeout(duration, unit);
        return this;
    }

    /**
     * @param timeInMillis time In Millis
     * @return fluent wait
     */
    public FluentWait atMost(long timeInMillis) {
        return atMost(timeInMillis, TimeUnit.MILLISECONDS);
    }

    public FluentWait pollingEvery(long duration, TimeUnit unit) {
        wait.pollingEvery(duration, unit);
        return this;
    }

    public FluentWait pollingEvery(long duration) {
        return pollingEvery(duration, TimeUnit.MILLISECONDS);
    }

    public FluentWait ignoreAll(java.util.Collection<java.lang.Class<? extends Throwable>> types) {
        wait.ignoreAll(types);
        return this;
    }

    public FluentWait ignoring(java.lang.Class<? extends java.lang.RuntimeException> exceptionType) {
        wait.ignoring(exceptionType);
        return this;
    }

    /**
     * Ignoring the two exceptions passed as params
     *
     * @param firstType  first type of exception which extends java.lang.RuntimeException
     * @param secondType second type of exception which extends java.lang.RuntimeException
     * @return this fluent wait
     */
    public FluentWait ignoring(java.lang.Class<? extends java.lang.RuntimeException> firstType,
                               java.lang.Class<? extends java.lang.RuntimeException> secondType) {
        wait.ignoring(firstType, secondType);
        return this;
    }

    /**
     * Wait until the predicate returns true.
     *
     * @param predicate predicate condition for wait
     */
    public void untilPredicate(Predicate<FluentControl> predicate) {
        updateWaitWithDefaultExceptions();
        wait.until(predicate);
    }

    /**
     * @param message - the failing message
     * @return fluent wait
     */
    public FluentWait withMessage(String message) {
        wait.withMessage(message);
        useCustomMessage = true;
        return this;
    }

    /**
     * @param message - the failing message supplier
     * @return fluent wait
     */
    public FluentWait withMessage(Supplier<String> message) {
        wait.withMessage(message);
        useCustomMessage = true;
        return this;
    }

    /**
     * Use this methods only to avoid ignoring StateElementReferenceException
     *
     * @return fluent wait
     */
    public FluentWait withNoDefaultsException() {
        useDefaultException = false;
        return this;
    }

    /**
     * Return a matcher configured to wait for particular condition for given element.
     *
     * @param element Element to wait for.
     * @return fluent wait matcher
     */
    public FluentConditions until(FluentWebElement element) {
        updateWaitWithDefaultExceptions();
        return WaitConditionProxy.element(this, "Element " + element.toString(), Suppliers.ofInstance(element));
    }

    /**
     * Return a matcher configured to wait for particular condition for given elements.
     *
     * @param elements Elements to wait for.
     * @return fluent wait matcher
     */
    public FluentListConditions until(final List<? extends FluentWebElement> elements) {
        updateWaitWithDefaultExceptions();
        return WaitConditionProxy.one(this, "Elements " + elements.toString(), Suppliers.<List<? extends FluentWebElement>>ofInstance(elements));
    }

    /**
     * Return a matcher configured to wait for particular condition for given elements.
     *
     * @param elements Elements to wait for.
     * @return fluent wait matcher
     */
    public FluentListConditions untilEach(final List<? extends FluentWebElement> elements) {
        updateWaitWithDefaultExceptions();
        return WaitConditionProxy.each(this, "Elements " + elements.toString(), Suppliers.<List<? extends FluentWebElement>>ofInstance(elements));
    }

    /**
     * Return a matcher configured to wait for particular condition for elements matching then given functional supplier.
     *
     * @param selector Supplier of the element to wait for.
     * @return fluent wait matcher
     */
    public FluentConditions untilElement(Supplier<? extends FluentWebElement> selector) {
        updateWaitWithDefaultExceptions();
        return WaitConditionProxy.element(this, "Element " + selector, selector);
    }

    /**
     * Return a matcher configured to wait for particular condition for elements matching then given functional supplier.
     *
     * @param selector Supplier of the element to wait for.
     * @return fluent wait matcher
     */
    public FluentListConditions untilElements(
            Supplier<? extends List<? extends FluentWebElement>> selector) {
        updateWaitWithDefaultExceptions();
        return WaitConditionProxy.one(this, "Elements " + selector.toString(), selector);
    }

    /**
     * Return a matcher configured to wait for particular condition for elements matching then given functional supplier.
     *
     * @param selector Supplier of the element to wait for.
     * @return fluent wait matcher
     */
    public FluentListConditions untilEachElements(
            Supplier<? extends List<? extends FluentWebElement>> selector) {
        updateWaitWithDefaultExceptions();
        return WaitConditionProxy.each(this, "Elements " + selector.toString(), selector);
    }

    @SuppressWarnings("unchecked")
    public FluentWaitWindowMatcher untilWindow(String windowName) {
        return new FluentWaitWindowMatcher(this, windowName);
    }

    /**
     * @return fluent wait page matcher
     */
    public FluentWaitPageMatcher untilPage() {
        updateWaitWithDefaultExceptions();
        return new FluentWaitPageMatcher(this, driver);
    }

    /**
     * @param page - the page to work with
     * @return fluent wait page matcher
     */
    public FluentWaitPageMatcher untilPage(FluentPage page) {
        updateWaitWithDefaultExceptions();
        return new FluentWaitPageMatcher(this, driver, page);
    }

    /**
     * Waits unconditionally for explicit amount of time. The method should be used only as a last resort. In most
     * cases you should wait for some condition, e.g. visibility of particular element on the page.
     *
     * @param amount   amount of time in milliseconds
     * @return {@code this} to allow chaining method invocations
     */
    public FluentWait explicitlyFor(long amount) {
        return explicitlyFor(amount, TimeUnit.MILLISECONDS);
    }

    /**
     * Waits unconditionally for explicit amount of time. The method should be used only as a last resort. In most
     * cases you should wait for some condition, e.g. visibility of particular element on the page.
     *
     * @param amount   amount of time
     * @param timeUnit unit of time
     * @return {@code this} to allow chaining method invocations
     */
    public FluentWait explicitlyFor(long amount, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(amount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    /**
     * Wait until the given condition is true.
     *
     * @param isTrue supplier of a condition returning a boolean.
     */
    public void until(final Supplier<Boolean> isTrue) {
        updateWaitWithDefaultExceptions();
        wait.until(new Function<Object, Boolean>() {
            public Boolean apply(Object input) {
                return isTrue.get();
            }

            public String toString() {
                return isTrue.toString();
            }
        });
    }

    /**
     * Wait until the given condition is true.
     *
     * @param isTrue function of a condition returning a boolean or any other object.
     */
    @Override
    public <T> T until(Function<? super FluentControl, T> isTrue) {
        updateWaitWithDefaultExceptions();
        return wait.until(isTrue);
    }

    private void updateWaitWithDefaultExceptions() {
        if (useDefaultException) {
            wait.ignoring(StaleElementReferenceException.class);
        }
    }

    public boolean useCustomMessage() {
        return useCustomMessage;
    }

}
