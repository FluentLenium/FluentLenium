package org.fluentlenium.core.wait;


import org.fluentlenium.core.search.Search;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public class FluentLeniumWait<T> implements org.openqa.selenium.support.ui.Wait<T> {

    private FluentWait wait;
    private Search search;

    public FluentWait getWait() {
        return wait;
    }

    public FluentLeniumWait(WebDriver driver, Search search) {
        wait = new FluentWait(driver);
        this.search = search;
    }

    public FluentLeniumWait<T> atMost(long duration, java.util.concurrent.TimeUnit unit) {
        wait.withTimeout(duration, unit);
        return this;
    }

    public FluentLeniumWait<T> pollingEvery(long duration, java.util.concurrent.TimeUnit unit) {
        wait.pollingEvery(duration, unit);
        return this;
    }

    public FluentLeniumWait<T> ignoreAll(java.util.Collection<java.lang.Class<? extends java.lang.RuntimeException>> types) {
        wait.ignoreAll(types);
        return this;

    }

    public FluentLeniumWait<T> ignoring(java.lang.Class<? extends java.lang.RuntimeException> exceptionType) {

        wait.ignoring(exceptionType);
        return this;

    }

    public FluentLeniumWait<T> ignoring(java.lang.Class<? extends java.lang.RuntimeException> firstType, java.lang.Class<? extends java.lang.RuntimeException> secondType) {
        wait.ignoring(firstType, secondType);
        return this;

    }

    public void until(com.google.common.base.Predicate<T> isTrue) {
        wait.until(isTrue);
    }

    public FluentWaitBuilder until(String string) {
        return new FluentWaitBuilder(search, wait, string);
    }

    public <V> V until(com.google.common.base.Function<? super T, V> isTrue) {
        return (V) wait.until(isTrue);
    }
}
