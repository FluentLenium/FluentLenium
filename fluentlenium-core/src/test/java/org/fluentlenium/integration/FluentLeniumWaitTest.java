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


import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.junit.Assert.fail;
import static org.fluentlenium.core.filter.FilterConstructor.*;

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

    public void checkAwaitHasTextWithText() {
        await().atMost(1, NANOSECONDS).until(".small").withText("Small 1").hasText("Small 1");
    }

    @Test
    public void checkAwaitContainsNameWithName() {
        await().atMost(1, NANOSECONDS).until(".small").withName("name").hasName("name");
    }


    @Test
    public void checkAwaitContainsNameWithClass() {
        await().atMost(1, NANOSECONDS).until("span").withClass("small").hasName("name");
    }


    @Test
    public void checkAwaitContainsNameWithClassRegex() {
        await().atMost(1, NANOSECONDS).until("span").withClass().contains(regex("smal?")).hasName("name");
    }

    @Test
    public void checkAwaitContainsNameWithClassAndContainsWord() {
        await().atMost(1, NANOSECONDS).until("span").withClass().containsWord("small").hasName("name");
    }

    @Test
    public void checkAwaitContainsTextWithText() {
        await().atMost(1, NANOSECONDS).until(".small").withText("Small 1").containsText("Small 1");
    }

    @Test
    public void checkUseCustomMessage() {
        try {
            await().withMessage("toto").atMost(1, NANOSECONDS).until(".small").withText("Small 1").containsText("Small 21");
            fail();
        } catch (TimeoutException e) {
            assertThat(e.getMessage()).contains("toto");
        }
    }


    @Test
    public void checkAwaitPageToLoad() {
        await().atMost(1, NANOSECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkAwaitPageIsAt() {
        FluentPage isAtJavascriptPage = createPage(MyFluentPage.class);
        isAtJavascriptPage.go();
        await().atMost(5, TimeUnit.SECONDS).untilPage(isAtJavascriptPage).isAt();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkAwaitPageToLoadWithNoJSEnabled() {
        FluentAdapter fluentTest = new FluentAdapter(new HtmlUnitDriver(false));
        fluentTest.await().atMost(1, NANOSECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkAwaitContainsText() {
        await().atMost(1, NANOSECONDS).until(".small").containsText("Small 1");
    }

    @Test
    public void checkAwaitContainsIdWithId() {
        await().atMost(1, NANOSECONDS).until(".small").withId("id2").hasId("id2");
    }


    @Test
    public void checkAwaitContainsIdWithIdContains() {
        await().atMost(1, NANOSECONDS).until(".small").withId().contains("id").hasSize(2);
    }

    @Test
    public void checkAwaitHasText() {
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
    public void when_a_element_is_not_present_then_isNotPresent_return_true() {
        await().atMost(1, NANOSECONDS).until(".small").withText().contains("notPresent").isNotPresent();
    }

    @Test(expected = TimeoutException.class)
    public void when_a_element_is_present_then_isNotPresent_throw_an_exception() {
        await().atMost(1, NANOSECONDS).until(".small").withText().contains("Small 1").isNotPresent();
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

    @Test
    public void when_element_is_present_then_isDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_displayed_then_isDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").areDisplayed();
    }

    @Test
    public void when_element_is_not_present_then_areNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#nonexistent").areNotDisplayed();
    }

    @Test
    public void when_element_is_not_displayed_then_areNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").areNotDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_displayed_then_areNotDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areNotDisplayed();
    }

    @Test
    public void when_element_is_enabled_then_areEnabled_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areEnabled();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_enabled_then_areEnabled_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#disabled").areEnabled();
    }
    
    @Test
    public void when_element_is_not_displayed_then_isPresent_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").isPresent();
    }

    @Test
    public void checkAwaitWithCssSelectorAndFilters() {
        await().atMost(1, NANOSECONDS).until(".small", withName("name2")).hasText("Small 2");
    }
    
    @Test
    public void checkAwaitWithFilters() {
        await().atMost(1, NANOSECONDS).until(withName("name2")).hasText("Small 2");
    }

    @Test(expected = TimeoutException.class)
    public void checkPolling() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).until("#default").hasText("wait");
    }


    @Test
    public void checkIsAt() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).untilPage(new FluentPage() {
            @Override
            public void isAt() {
            }
        }).isAt();
    }

    @Test
    public void checkLoaded() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkPredicate() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).until(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent o) {
                return true;
            }
        })
        ;
    }

    @Test
    public void checkFunction() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).until(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent fluent) {
                return true;
            }
        });
    }


}

class MyFluentPage extends FluentPage {
    @Override
    public void isAt() {
        assertThat(find("#newField").getTexts()).contains("new");
    }

    @Override
    public String getUrl() {
        return LocalFluentCase.JAVASCRIPT_URL;
    }
}
