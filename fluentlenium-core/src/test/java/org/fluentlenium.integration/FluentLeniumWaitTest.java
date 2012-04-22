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

package org.fluentlenium.integration;


import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;

public class FluentLeniumWaitTest extends LocalFluentCase {
    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Test
    public void checkAwaitIsPresent() {
        await().atMost(1, NANOSECONDS).until(".small").isPresent();
    }

    @Test
    public void checkAwaitHasSize() {
        await().atMost(1, NANOSECONDS).until(".small").hasSize(3);
    }

    @Test
    public void checkAwaitContainsTextWithText() {
        await().atMost(1, NANOSECONDS).until(".small").withText("Small 1").hasText("Small 1");
    }

    @Test
    public void checkAwaitContainsNameWithName() {
        await().atMost(1, NANOSECONDS).until(".small").withName("name").hasName("name");
    }

    @Test
    public void checkAwaitContainsIdWithId() {
        await().atMost(1, NANOSECONDS).until(".small").withId("id2").hasId("id2");
    }

    @Test
    public void checkAwaitContainsText() {
        await().atMost(1, NANOSECONDS).until(".small").hasText("Small 1");
    }

    @Test
    public void checkAwaitContainsName() {
        await().atMost(1, NANOSECONDS).until(".small").hasName("name");
    }

    @Test
    public void checkAwaitContainsId() {
        await().atMost(1, NANOSECONDS).until(".small").hasId("id2");
    }

    @Test
    public void checkAwaitContainsTextWithTextMatcher() {

        await().atMost(1, NANOSECONDS).until(".small").withText().contains("Small 1").isPresent();
    }


    @Test
    public void checkAwaitStartWithRegex() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").startsWith(regex("id")).hasSize(2);
    }

    @Test
    public void checkAwaitNotStartWith() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notStartsWith("id").hasSize(1);
    }

    @Test
    public void checkAwaitNotStartWithRegex() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notStartsWith(regex("id")).hasSize(1);
    }


    @Test
    public void checkAwaitEndsWithRegex() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").endsWith(regex("2")).hasSize(1);
    }

    @Test
    public void checkAwaitNotEndsWith() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notEndsWith("2").hasId("id");
    }

    @Test
    public void checkAwaitNotEndsWithRegex() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notEndsWith(regex("2")).hasId("id");
    }

    @Test
    public void checkAwaitNotContains() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notContains("d").hasSize(1);
    }

    @Test
    public void checkAwaitNotContainsRegex() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notContains(regex("d")).hasSize(1);
    }

    @Test
    public void checkAwaitEquals() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notContains("d").hasSize().equalTo(1);
    }

    @Test
    public void checkAwaitNotEquals() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notContains("d").hasSize().notEqualTo(10);
    }

    @Test
    public void checkAwaitLessThan() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notContains("d").hasSize().lessThan(4);
    }

    @Test
    public void checkAwaitLessThanOrEquals() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notContains("d").hasSize().lessThanOrEqualTo(1);
    }

    @Test
    public void checkAwaitGreaterThan() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notContains("d").hasSize().greaterThan(-1);
    }

    @Test
    public void checkAwaitGreaterThanOrEquals() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").notContains("d").hasSize().greaterThanOrEqualTo(1);
    }

    @Test
    public void checkWithValue() {
        await().atMost(1, NANOSECONDS).until("input").with("value").equalTo("John").hasSize(4);
    }

    @Test
    public void checkMultipleFilter() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").startsWith(regex("id")).with("text").endsWith("2").hasSize(1);
    }

    @Test
    public void checkHasAttribute() {
        await().atMost(1, NANOSECONDS).until("input").hasAttribute("value", "John");
    }

    @Test
    public void checkHasAttributeWithOthersFilters() {
        await().atMost(1, NANOSECONDS).until("input").with("value").equalTo("John").hasAttribute("value", "John");
    }

    @Test(expected = TimeoutException.class)
    public void checkPolling() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).until("#default").hasText("wait");
    }
}
