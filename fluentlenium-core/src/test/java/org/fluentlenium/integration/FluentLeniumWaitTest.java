package org.fluentlenium.integration;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.with;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.junit.Assert.fail;

public class FluentLeniumWaitTest extends IntegrationFluentTest {

    private static final int MINIMAL_TIMEOUT = 1;
    public static final int SECOND_TIMEOUT = 1000;
    public static final int SMALL_EXPECTED_SIZE = 3;
    public static final int FIVE_SECONDS_TIMEOUT = 5;
    public static final int EXACTLY_ONE = 1;
    public static final int INVALID_EQUALS_NUMBER = 10;
    public static final int LESS_THAN_FOUR = 4;
    public static final int LESS_THAN_OR_EQ_1 = 1;
    public static final int GREATER_THAN_MINUS_ONE = -1;
    public static final int JOHN_FOUR_MATCHED = 4;
    public static final int ONLY_ONE_ENDS_WITH_TWO = 1;
    public static final int SECOND_AND_A_HALF_TIMEOUT = 1500;
    public static final int LESS_THAN_SECOND_TIMEOUT = 800;

    @Before
    public void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void checkAwaitIsPresent() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).present();
    }

    @Test
    public void checkAwaitIsNotPresent() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".not-present")).not().present();
    }

    @Test
    public void checkAwaitIsDisplayed() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).displayed();
    }

    @Test
    public void checkAwaitIsNotDisplayed() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#hidden")).not().displayed();
    }

    @Test
    public void checkAwaitIsClickable() throws Exception {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).clickable();
    }

    @Test
    public void checkAwaitIsNotClickable() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("input[disabled]")).not().clickable();
    }

    @Test
    public void checkAwaitIsSelected() throws Exception {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#selected")).selected();
    }

    @Test
    public void checkAwaitIsNotSelected() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#non_selected")).not().selected();
    }

    @Test(expected = TimeoutException.class)
    public void checkAwaitDisabledIsClickableThrowTimeoutException() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("input[disabled]")).clickable();
    }

    @Test
    public void explicitWait() {
        await().explicitlyFor(MINIMAL_TIMEOUT, NANOSECONDS);
    }

    @Test
    public void explicitWaitMillis() {
        await().explicitlyFor(MINIMAL_TIMEOUT);
    }

    @Test
    public void checkAwaitHasSize() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small")).size(SMALL_EXPECTED_SIZE);
    }

    @Test
    public void checkAwaitHasTextWithText() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", withText("Small 1"))).text().equalTo("Small 1");
    }

    @Test
    public void checkAwaitContainsNameWithName() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", withName("name"))).name("name");
    }

    @Test
    public void checkAwaitContainsNameWithClass() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("span", withClass("small"))).name("name");
    }

    @Test
    public void checkAwaitContainsNameWithClassRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("span", withClass().contains(regex("smal?")))).name("name");
    }

    @Test
    public void checkAwaitContainsNameWithClassAndContainsWord() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("span", withClass().containsWord("small"))).name("name");
    }

    @Test
    public void checkAwaitContainsTextWithText() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", withText("Small 1"))).text().contains("Small 1");
    }

    @Test
    public void checkUseCustomMessage() {
        try {
            await().withMessage("toto").atMost(1, NANOSECONDS).until($(".small", withText("Small 1"))).text()
                    .contains("Small 21");
            fail();
        } catch (final TimeoutException e) {
            assertThat(e.getMessage()).contains("toto");
        }
    }

    @Test
    public void checkAwaitPageToLoad() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkAwaitPageIsAt() {
        final FluentPage isAtJavascriptPage = newInstance(MyFluentPage.class);
        isAtJavascriptPage.go();
        await().atMost(FIVE_SECONDS_TIMEOUT, TimeUnit.SECONDS).untilPage(isAtJavascriptPage).isAt();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkAwaitPageToLoadWithNoJSEnabled() {
        final FluentAdapter adapter = new FluentAdapter();
        adapter.initFluent(new HtmlUnitDriver(false));
        adapter.await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkAwaitContainsText() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).text().contains("Small 1");
    }

    @Test
    public void checkAwaitContainsTextAlternative() {
        $(".small").await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until().text().contains("Small 1");
        $(".small", withText("Small 1")).await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach().text().contains("Small 1");
        el(".small").await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until().text().contains("Small 1");
    }

    @Test
    public void checkAwaitContainsIdWithId() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", withId("id2"))).id("id2");
    }

    @Test
    public void checkAwaitNameStartsWith() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", withName().startsWith("name"))).size(2);
    }

    @Test
    public void checkAwaitContainsIdWithIdContains() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", withId().contains("id"))).size(2);
    }

    @Test
    public void checkAwaitHasText() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).text().equalTo("Small 1");
    }

    @Test
    public void checkAwaitContainsName() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).name("name");
    }

    @Test
    public void checkAwaitContainsId() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).id("id2");
    }

    @Test
    public void checkAwaitContainsTextWithTextMatcher() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", withText().contains("Small 1"))).present();
    }

    @Test
    public void whenAElementIsNotPresentThenIsNotPresentReturnTrue() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", withText().contains("notPresent"))).not().present();
    }

    @Test(expected = TimeoutException.class)
    public void whenAElementIsPresentThenIsNotPresentThrowAnException() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", withText().contains("Small 1"))).not().present();
    }

    @Test
    public void checkAwaitStartWithRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", with("id").startsWith(regex(".d")))).size(2);
    }

    @Test
    public void checkAwaitStartWithString() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", with("id").startsWith("id"))).size(2);
    }

    @Test
    public void checkAwaitNotStartWith() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", with("id").notStartsWith("id"))).size(1);
    }

    @Test
    public void checkAwaitNotStartWithRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", with("id").notStartsWith(regex("id")))).size(1);
    }

    @Test
    public void checkAwaitEndsWithRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", with("id").endsWith(regex("2")))).size(1);
    }

    @Test
    public void checkAwaitNotEndsWith() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", with("id").notEndsWith("2"))).id("id");
    }

    @Test
    public void checkAwaitNotEndsWithRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", with("id").notEndsWith(regex("2")))).id("id");
    }

    @Test
    public void checkAwaitNotContains() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", with("id").notContains("d"))).
                size(EXACTLY_ONE);
    }

    @Test
    public void checkAwaitNotContainsRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", with("id").notContains(regex("d")))).
                size(EXACTLY_ONE);
    }

    @Test
    public void checkAwaitEquals() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", with("id").notContains("d"))).size().
                equalTo(EXACTLY_ONE);
    }

    @Test
    public void checkAwaitNotEquals() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", with("id").notContains("d"))).size().not().
                equalTo(INVALID_EQUALS_NUMBER);
    }

    @Test
    public void checkAwaitLessThan() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", with("id").notContains("d"))).size().
                lessThan(LESS_THAN_FOUR);
    }

    @Test
    public void checkAwaitLessThanOrEquals() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", with("id").notContains("d"))).size().
                lessThanOrEqualTo(LESS_THAN_OR_EQ_1);
    }

    @Test
    public void checkAwaitGreaterThan() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", with("id").notContains("d"))).size().
                greaterThan(GREATER_THAN_MINUS_ONE);
    }

    @Test
    public void checkAwaitGreaterThanOrEquals() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", with("id").notContains("d"))).size().
                greaterThanOrEqualTo(1);
    }

    @Test
    public void checkWithValue() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("input", with("value").equalTo("John"))).size(JOHN_FOUR_MATCHED);
    }

    @Test
    public void checkMultipleFilter() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                .untilEach($(".small", with("id").startsWith(regex("id")), with("text").endsWith("2")))
                .size(ONLY_ONE_ENDS_WITH_TWO);
    }

    @Test
    public void checkHasAttribute() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("input")).attribute("value", "John");
    }

    @Test
    public void checkHasAttributeWithOthersFilters() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("input", with("value").equalTo("John"))).attribute("value", "John");
    }

    @Test
    public void whenElementIsPresentThenAreDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#default")).displayed();
    }

    @Test
    public void whenElementIsPresentThenIsDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#default")).displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotDisplayedThenAreDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#unvisible")).displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotDisplayedThenIsDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#unvisible")).displayed();
    }

    @Test
    public void whenElementIsNotPresentThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#nonexistent")).not().displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotPresentThenAreDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#nonexistent")).displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotPresentThenAreEnabledThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#nonexistent")).enabled();
    }

    @Test
    public void whenElementIsNotDisplayedThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#unvisible")).not().displayed();
    }

    @Test
    public void whenElementIsNotDisplayedThenIsNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#unvisible")).not().displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsDisplayedThenAreNotDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#default")).not().displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsDisplayedThenIsNotDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#default")).not().displayed();
    }

    @Test
    public void whenElementIsEnabledThenAreEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#default")).enabled();
    }

    @Test
    public void whenElementIsEnabledThenIsEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#default")).enabled();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotEnabledThenAreEnabledThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#disabled")).enabled();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotEnabledThenIsEnabledThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#disabled")).enabled();
    }

    @Test
    public void whenElementIsSelectedThenIsSelectedReturnTrue() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#selected")).selected();
    }

    @Test
    public void whenElementIsNotSelectedThenIsNotSelectedReturnTrue() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#non_selected")).not().selected();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotSelectedThenAreSelectedThrowsException() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#non_selected")).selected();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotSelectedThenIsSelectedThrowsException() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#non_selected")).selected();
    }

    @Test(expected = TimeoutException.class)
    public void checkPolling() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_AND_A_HALF_TIMEOUT, TimeUnit.MILLISECONDS).until($("#default")).text().equalTo("wait");
    }

    @Test
    public void checkIsAt() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).untilPage(new FluentPage()).isAt();
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIsAtWithNullPage() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).untilPage().isAt();
    }

    @Test
    public void checkLoaded() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkPredicate() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(LESS_THAN_SECOND_TIMEOUT, TimeUnit.MILLISECONDS).untilPredicate(new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl o) {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkPredicateFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(SECOND_TIMEOUT).untilPredicate(new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl o) {
                return false;
            }
        });
    }

    @Test
    public void checkFunction() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).until(new Function<FluentControl, Boolean>() {
            @Override
            public Boolean apply(final FluentControl fluent) {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkFunctionFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(SECOND_TIMEOUT).until(new Function<FluentControl, Boolean>() {
            @Override
            public Boolean apply(final FluentControl fluent) {
                return false;
            }
        });
    }

    @Test
    public void checkSupplier() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).until(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkSupplierFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(SECOND_TIMEOUT).until(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                return false;
            }
        });
    }

    @Test
    public void seleniumWaitIsAvailable() {
        final FluentWait wait = await().getWait();
        assertThat(wait).isInstanceOf(FluentWait.class);
    }

    private static class MyFluentPage extends FluentPage {
        @Override
        public void isAt() {
            assertThat(find("#newField").texts()).contains("new");
        }

        @Override
        public String getUrl() {
            return IntegrationFluentTest.JAVASCRIPT_URL;
        }
    }

}


