package org.fluentlenium.core.wait;


import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class FluentWait implements org.openqa.selenium.support.ui.Wait<Fluent> {

    private org.openqa.selenium.support.ui.FluentWait wait;
    private Search search;
    private WebDriver driver;

    public org.openqa.selenium.support.ui.FluentWait getWait() {
        return wait;
    }

    public FluentWait(WebDriver driver, Search search) {
        wait = new org.openqa.selenium.support.ui.FluentWait(driver);
        this.search = search;
        this.driver = driver;
    }

    public FluentWait atMost(long duration, java.util.concurrent.TimeUnit unit) {
        wait.withTimeout(duration, unit);
        return this;
    }

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

    public FluentWait ignoring(java.lang.Class<? extends java.lang.RuntimeException> firstType, java.lang.Class<? extends java.lang.RuntimeException> secondType) {
        wait.ignoring(firstType, secondType);
        return this;

    }

    public FluentWait until(com.google.common.base.Predicate isTrue) {
        wait.until(isTrue);
        return this;
    }

    public FluentWaitMatcher until(String string) {
        return new FluentWaitMatcher(search, wait, string);
    }

    public FluentWaitPageMatcher untilPage() {
        return new FluentWaitPageMatcher(wait, driver);
    }

    public FluentWaitPageMatcher untilPage(FluentPage page) {
        return new FluentWaitPageMatcher(wait, driver, page);
    }

    public <V> V until(com.google.common.base.Function<? super Fluent, V> isTrue) {
        return (V) wait.until(isTrue);
    }
}
