package org.fluentlenium.core.wait;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.Beta;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class FluentWait implements org.openqa.selenium.support.ui.Wait<Fluent> {

    private final org.openqa.selenium.support.ui.FluentWait<Fluent> wait;
    private final Search search;
    private final WebDriver driver;
    private boolean useDefaultException;
    private boolean useCustomMessage;

    public org.openqa.selenium.support.ui.FluentWait getWait() {
        return wait;
    }

    public FluentWait(Fluent fluent, Search search) {
        wait = new org.openqa.selenium.support.ui.FluentWait<Fluent>(fluent);
        this.search = search;
        this.driver = fluent.getDriver();
        useDefaultException = true;
    }


    public FluentWait atMost(long duration, java.util.concurrent.TimeUnit unit) {
        wait.withTimeout(duration, unit);
        return this;
    }

    /**
     * @param timeInMillis time In Millis
     * @return fluent wait
     */
    public FluentWait atMost(long timeInMillis) {
        wait.withTimeout(timeInMillis, TimeUnit.MILLISECONDS);
        return this;
    }

    public FluentWait pollingEvery(long duration, java.util.concurrent.TimeUnit unit) {
        wait.pollingEvery(duration, unit);
        return this;
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
     * @return
     */
    public FluentWait ignoring(java.lang.Class<? extends java.lang.RuntimeException> firstType, java.lang.Class<? extends java.lang.RuntimeException> secondType) {
        wait.ignoring(firstType, secondType);
        return this;

    }

    /**
     * @param isTrue predicate condition for wait
     * @return fluent wait
     * @deprecated This method is java8 lambda ambigous with {@link #until(Function)} and will be removed.
     */
    @Deprecated
    public FluentWait until(Predicate<Fluent> isTrue) {
        return untilPredicate(isTrue);
    }

    /**
     * @param isTrue predicate condition for wait
     * @return fluent wait
     */
    public FluentWait untilPredicate(Predicate<Fluent> isTrue) {
        updateWaitWithDefaultExceptions();
        wait.until(isTrue);
        return this;
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
     * Use this methods only to avoid ignoring StateElementReferenceException
     *
     * @return fluent wait
     */
    @Beta
    public FluentWait withNoDefaultsException() {
        useDefaultException = false;
        return this;
    }

    /**
     * @param string - CSS selector
     * @return fluent wait matcher
     */
    public FluentWaitMatcher until(String string) {
        updateWaitWithDefaultExceptions();
        return new FluentWaitMatcher(search, this, string);
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
     * Return the current driver
     *
     * @return web driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public <T> T until(Function<? super Fluent, T> isTrue) {
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
