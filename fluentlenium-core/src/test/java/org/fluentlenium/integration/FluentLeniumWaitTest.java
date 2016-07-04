package org.fluentlenium.integration;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.junit.Assert.fail;

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
    public void checkAwaitIsNotPresent() {
        await().atMost(1, NANOSECONDS).until(".not-present").isNotPresent();
    }

    @Test
    public void checkAwaitIsDisplayed() {
        await().atMost(1, NANOSECONDS).until(".small").isDisplayed();
    }

    @Test
    public void checkAwaitIsNotDisplayed() {
        await().atMost(1, NANOSECONDS).until("#hidden").isNotDisplayed();
    }

    @Test
    public void checkAwaitIsClickable() throws Exception {
        await().atMost(1, NANOSECONDS).until(".small").isClickable();
    }

    @Test
    public void checkAwaitIsNotClickable() {
        await().atMost(1, NANOSECONDS).until("input[disabled]").isNotClickable();
    }

    @Test
    public void checkAwaitIsSelected() throws Exception {
        await().atMost(1, NANOSECONDS).until("#selected").isSelected();
    }

    @Test
    public void checkAwaitIsNotSelected() {
        await().atMost(1, NANOSECONDS).until("#non_selected").isNotSelected();
    }

    @Test(expected = TimeoutException.class)
    public void checkAwaitDisabledIsClickableThrowTimeoutException() {
        await().atMost(1, NANOSECONDS).until("input[disabled]").isClickable();
    }

    @Test
    public void explicitWait() {
        await().explicitlyFor(1, NANOSECONDS);
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
            await().withMessage("toto").atMost(1, NANOSECONDS).until(".small").withText("Small 1")
                    .containsText("Small 21");
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
    public void checkAwaitNameStartsWith() {
        await().atMost(1, NANOSECONDS).until(".small").withName().startsWith("name").hasSize(2);
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
        await().atMost(1, NANOSECONDS).until(".small").with("id").startsWith(regex(".d")).hasSize(2);
    }

    @Test
    public void checkAwaitStartWithString() {
        await().atMost(1, NANOSECONDS).until(".small").with("id").startsWith("id").hasSize(2);
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
        await().atMost(1, NANOSECONDS).until(".small").with("id").startsWith(regex("id")).with("text").endsWith("2")
                .hasSize(1);
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
    public void when_element_is_present_then_areDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areDisplayed();
    }

    @Test
    public void when_element_is_present_then_isDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").isDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_displayed_then_areDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").areDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_displayed_then_isDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").isDisplayed();
    }

    @Test
    public void when_element_is_not_present_then_areNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#nonexistent").areNotDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_present_then_areDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#nonexistent").areDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_present_then_areEnabled_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#nonexistent").areEnabled();
    }

    @Test
    public void when_element_is_not_displayed_then_areNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").areNotDisplayed();
    }

    @Test
    public void when_element_is_not_displayed_then_isNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").isNotDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_displayed_then_areNotDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areNotDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_displayed_then_isNotDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").isNotDisplayed();
    }

    @Test
    public void when_element_is_enabled_then_areEnabled_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areEnabled();
    }

    @Test
    public void when_element_is_enabled_then_isEnabled_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").isEnabled();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_enabled_then_areEnabled_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#disabled").areEnabled();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_enabled_then_isEnabled_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#disabled").isEnabled();
    }

    @Test
    public void when_element_is_selected_then_isSelected_return_true() {
        await().atMost(1, NANOSECONDS).until("#selected").isSelected();
    }

    @Test
    public void when_element_is_not_selected_then_isNotSelected_return_true() {
        await().atMost(1, NANOSECONDS).until("#non_selected").isNotSelected();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_selected_then_areSelected_throws_exception() {
        await().atMost(1, NANOSECONDS).until("#non_selected").areSelected();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_selected_then_isSelected_throws_exception() {
        await().atMost(1, NANOSECONDS).until("#non_selected").isSelected();
    }

    @Test(expected = TimeoutException.class)
    public void checkPolling() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1500, TimeUnit.MILLISECONDS).until("#default").hasText("wait");
    }

    @Test
    public void checkIsAt() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage(new FluentPage() {
            @Override
            public void isAt() {
            }
        }).isAt();
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIsAtWithNullPage() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage().isAt();
    }

    @Test
    public void checkLoaded() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkPredicate() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).untilPredicate(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent o) {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkPredicateFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).untilPredicate(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent o) {
                return false;
            }
        });
    }

    @Test
    public void checkFunction() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).until(new Function<Fluent, Boolean>() {
            @Override
            public Boolean apply(Fluent fluent) {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkFunctionFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).until(new Function<Fluent, Boolean>() {
            @Override
            public Boolean apply(Fluent fluent) {
                return false;
            }
        });
    }

    @Test
    public void checkSupplier() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).until(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkSupplierFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).until(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                return false;
            }
        });
    }

    @Test
    public void seleniumWaitIsAvailable() {
        FluentWait wait = await().getWait();
        assertThat(wait).isInstanceOf(FluentWait.class);
    }

    private static class MyFluentPage extends FluentPage {
        @Override
        public void isAt() {
            assertThat(find("#newField").getTexts()).contains("new");
        }

        @Override
        public String getUrl() {
            return LocalFluentCase.JAVASCRIPT_URL;
        }
    }

}


