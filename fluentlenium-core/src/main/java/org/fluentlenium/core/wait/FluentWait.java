/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fluentlenium.core.wait;


import com.google.common.base.Function;

import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.filter.Filter;
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
     * @return
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
     * @param firstType
     * @param secondType
     * @return
     */
    public FluentWait ignoring(java.lang.Class<? extends java.lang.RuntimeException> firstType, java.lang.Class<? extends java.lang.RuntimeException> secondType) {
        wait.ignoring(firstType, secondType);
        return this;

    }

    /**
     * @param isTrue
     * @return
     */
    public FluentWait until(com.google.common.base.Predicate<Fluent> isTrue) {
        updateWaitWithDefaultExceptions();
        wait.until(isTrue);
        return this;
    }

    /**
     * @param message - the failing message
     * @return
     */
    public FluentWait withMessage(String message) {
        wait.withMessage(message);
        useCustomMessage = true;
        return this;
    }

    /**
     * Use this methods only to avoid ignoring StateElementReferenceException
     *
     * @return
     */
    @Beta
    public FluentWait withNoDefaultsException() {
        useDefaultException = false;
        return this;
    }

    /**
     * @param string - CSS selector
     * @return
     */
    public FluentWaitMatcher until(String string) {
        updateWaitWithDefaultExceptions();
        return new FluentWaitMatcher(search, this, string);
    }

    /**
     * @param string - CSS selector
     * @param filters
     * @return
     */
    public FluentWaitMatcher until(String string, Filter... filters) {
        updateWaitWithDefaultExceptions();
        return new FluentWaitMatcher(search, this, string, filters);
    }

    /**
     * @param filters
     * @return
     */
    public FluentWaitMatcher until(Filter... filters) {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("cssSelector or filter is required");
        }
        return until("*", filters);
    }
    
    /**
     * @return
     */
    public FluentWaitPageMatcher untilPage() {
        updateWaitWithDefaultExceptions();
        return new FluentWaitPageMatcher(this, driver);
    }

    /**
     * @param page - the page to work with
     * @return
     */
    public FluentWaitPageMatcher untilPage(FluentPage page) {
        updateWaitWithDefaultExceptions();
        return new FluentWaitPageMatcher(this, driver, page);
    }

    /**
     * Return the current driver
     *
     * @return
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
