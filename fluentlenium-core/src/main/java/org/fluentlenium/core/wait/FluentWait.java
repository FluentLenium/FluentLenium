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
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class FluentWait implements org.openqa.selenium.support.ui.Wait<Fluent> {

    private final org.openqa.selenium.support.ui.FluentWait<Fluent> wait;
    private final Search search;
    private final WebDriver driver;

    public org.openqa.selenium.support.ui.FluentWait getWait() {
        return wait;
    }

    public FluentWait(Fluent fluent, Search search) {
        wait = new org.openqa.selenium.support.ui.FluentWait<Fluent>(fluent);
        this.search = search;
        this.driver = fluent.getDriver();
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

    public FluentWait until(com.google.common.base.Predicate<Fluent> isTrue) {
        wait.until(isTrue);
        return this;
    }

    public FluentWait withMessage(String message) {
        wait.withMessage(message);
        return this;
    }

    public FluentWaitMatcher until(String string) {
        return new FluentWaitMatcher(search, this, string);
    }


    public FluentWaitPageMatcher untilPage() {
        return new FluentWaitPageMatcher(this, driver);
    }

    public FluentWaitPageMatcher untilPage(FluentPage page) {
        return new FluentWaitPageMatcher(this, driver, page);
    }


    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public <T> T until(Function<? super Fluent, T> isTrue) {
        return wait.until(isTrue);
    }
}
