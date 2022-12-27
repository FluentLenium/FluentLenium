package io.fluentlenium.test.await;

import io.fluentlenium.core.FluentPage;import io.fluentlenium.core.filter.FilterConstructor;import io.fluentlenium.core.filter.MatcherConstructor;import net.jcip.annotations.NotThreadSafe;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.with;
import static io.fluentlenium.core.filter.FilterConstructor.withClass;
import static io.fluentlenium.core.filter.FilterConstructor.withId;
import static io.fluentlenium.core.filter.FilterConstructor.withName;
import static io.fluentlenium.core.filter.FilterConstructor.withText;
import static io.fluentlenium.core.filter.FilterConstructor.withTextContent;
import static io.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@NotThreadSafe
@SuppressWarnings("PMD.Excessiveount")
class FluentLeniumWaitTest extends IntegrationFluentTest {

    private static final int MINIMAL_TIMEOUT = 1;
    private static final int SECOND_TIMEOUT = 1000;
    private static final int SMALL_EXPECTED_SIZE = 3;
    private static final int FIVE_SECONDS_TIMEOUT = 5;
    private static final int EXACTLY_ONE = 1;
    private static final int INVALID_EQUALS_NUMBER = 10;
    private static final int LESS_THAN_FOUR = 4;
    private static final int LESS_THAN_OR_EQ_1 = 1;
    private static final int GREATER_THAN_MINUS_ONE = -1;
    private static final int JOHN_FOUR_MATCHED = 4;
    private static final int ONLY_ONE_ENDS_WITH_TWO = 1;
    private static final int SECOND_AND_A_HALF_TIMEOUT = 1500;
    private static final int LESS_THAN_SECOND_TIMEOUT = 800;

    @BeforeEach
    void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    void checkAwaitIsPresent() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).present();
    }

    @Test
    void checkAwaitIsNotPresent() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".not-present")).not().present();
    }

    @Test
    void checkAwaitIsDisplayed() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).displayed();
    }

    @Test
    void checkAwaitIsNotDisplayed() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#hidden")).not().displayed();
    }

    @Test
    void checkAwaitIsClickable() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).clickable();
    }

    @Test
    void checkAwaitIsNotClickable() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("input[disabled]")).not().clickable();
    }

    @Test
    void checkAwaitIsSelected() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#selected")).selected();
    }

    @Test
    void checkAwaitIsNotSelected() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#non_selected")).not().selected();
    }

    @Test
    void checkAwaitDisabledIsClickableThrowTimeoutException() {
        assertThrows(TimeoutException.class,
                () -> await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                        .until($("input[disabled]")).clickable());
    }

    @Test
    void explicitWait() {
        await().explicitlyFor(MINIMAL_TIMEOUT, NANOSECONDS);
    }

    @Test
    void explicitWaitMillis() {
        await().explicitlyFor(MINIMAL_TIMEOUT);
    }

    @Test
    void checkAwaitHasSize() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small")).size(SMALL_EXPECTED_SIZE);
    }

    @Test
    void checkAwaitHasTextWithText() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.withText("Small 1"))).text().equalTo("Small 1");
    }

    @Test
    void checkAwaitContainsNameWithName() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.withName("name"))).name("name");
    }

    @Test
    void checkAwaitContainsNameWithClass() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("span", FilterConstructor.withClass("small"))).name("name");
    }

    @Test
    void checkAwaitContainsNameWithClassRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("span", FilterConstructor.withClass().contains(MatcherConstructor.regex("smal?")))).name("name");
    }

    @Test
    void checkAwaitContainsNameWithClassAndContainsWord() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("span", FilterConstructor.withClass().containsWord("small"))).name("name");
    }

    @Test
    void checkAwaitContainsTextWithText() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.withText("Small 1"))).text().contains("Small 1");
    }

    @Test
    void checkUseCustomMessage() {
        try {
            await().withMessage("toto").atMost(1, NANOSECONDS).until($(".small", FilterConstructor.withText("Small 1"))).text()
                    .contains("Small 21");
            fail();
        } catch (TimeoutException e) {
            assertThat(e.getMessage()).contains("toto");
        }
    }

    @Test
    void checkAwaitPageToLoad() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilPage().isLoaded();
    }

    @Test
    void checkAwaitPageIsAt() {
        FluentPage isAtJavascriptPage = newInstance(MyFluentPage.class);
        isAtJavascriptPage.go();
        await().atMost(FIVE_SECONDS_TIMEOUT, TimeUnit.SECONDS).untilPage(isAtJavascriptPage).isAt();
    }

    @Test
    void checkAwaitContainsText() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).text().contains("Small 1");
    }

    @Test
    void checkAwaitContainsTextAlternative() {
        $(".small").await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until().text().contains("Small 1");
        $(".small", FilterConstructor.withText("Small 1")).await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach().text().contains("Small 1");
        el(".small").await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until().text().contains("Small 1");
    }

    @Test
    void checkAwaitContainsIdWithId() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.withId("id2"))).id("id2");
    }

    @Test
    void checkAwaitNameStartsWith() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.withName().startsWith("name"))).size(2);
    }

    @Test
    void checkAwaitContainsIdWithIdContains() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.withId().contains("id"))).size(2);
    }

    @Test
    void checkAwaitHasText() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).text().equalTo("Small 1");
    }

    @Test
    void checkAwaitContainsName() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).name("name");
    }

    @Test
    void checkAwaitContainsId() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small")).id("id2");
    }

    @Test
    void checkAwaitContainsTextWithTextMatcher() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.withText().contains("Small 1"))).present();
    }

    @Test
    void checkAwaitContainsTextWithTextContentMatcher() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.withTextContent().contains("Small 1"))).present();
    }

    @Test
    void whenAElementIsNotPresentThenIsNotPresentReturnTrue() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.withText().contains("notPresent"))).not().present();
    }

    @Test
    void whenAElementIsPresentThenIsNotPresentThrowAnException() {
        assertThrows(TimeoutException.class,
                () -> await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                        .until($(".small", FilterConstructor.withText().contains("Small 1")))
                        .not().present());
    }

    @Test
    void checkAwaitStartWithRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.with("id").startsWith(MatcherConstructor.regex(".d")))).size(2);
    }

    @Test
    void checkAwaitStartWithString() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.with("id").startsWith("id"))).size(2);
    }

    @Test
    void checkAwaitNotStartWith() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.with("id").notStartsWith("id"))).size(1);
    }

    @Test
    void checkAwaitNotStartWithRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.with("id").notStartsWith(MatcherConstructor.regex("id")))).size(1);
    }

    @Test
    void checkAwaitEndsWithRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.with("id").endsWith(MatcherConstructor.regex("2")))).size(1);
    }

    @Test
    void checkAwaitNotEndsWith() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.with("id").notEndsWith("2"))).id("id");
    }

    @Test
    void checkAwaitNotEndsWithRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.with("id").notEndsWith(MatcherConstructor.regex("2")))).id("id");
    }

    @Test
    void checkAwaitNotContains() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.with("id").notContains("d"))).
                size(EXACTLY_ONE);
    }

    @Test
    void checkAwaitNotContainsRegex() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($(".small", FilterConstructor.with("id").notContains(MatcherConstructor.regex("d")))).
                size(EXACTLY_ONE);
    }

    @Test
    void checkAwaitEquals() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.with("id").notContains("d"))).size().
                equalTo(EXACTLY_ONE);
    }

    @Test
    void checkAwaitNotEquals() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.with("id").notContains("d"))).size().not().
                equalTo(INVALID_EQUALS_NUMBER);
    }

    @Test
    void checkAwaitLessThan() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.with("id").notContains("d"))).size().
                lessThan(LESS_THAN_FOUR);
    }

    @Test
    void checkAwaitLessThanOrEquals() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.with("id").notContains("d"))).size().
                lessThanOrEqualTo(LESS_THAN_OR_EQ_1);
    }

    @Test
    void checkAwaitGreaterThan() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.with("id").notContains("d"))).size().
                greaterThan(GREATER_THAN_MINUS_ONE);
    }

    @Test
    void checkAwaitGreaterThanOrEquals() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($(".small", FilterConstructor.with("id").notContains("d"))).size().
                greaterThanOrEqualTo(1);
    }

    @Test
    void checkWithValue() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("input", FilterConstructor.with("value").equalTo("John"))).size(JOHN_FOUR_MATCHED);
    }

    @Test
    void checkMultipleFilter() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                .untilEach($(".small", FilterConstructor.with("id").startsWith(MatcherConstructor.regex("id")), FilterConstructor.with("text").endsWith("2")))
                .size(ONLY_ONE_ENDS_WITH_TWO);
    }

    @Test
    void checkHasAttribute() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("input")).attribute("value", "John");
    }

    @Test
    void checkHasAttributeWithOthersFilters() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("input", FilterConstructor.with("value").equalTo("John"))).attribute("value", "John");
    }

    @Test
    void whenElementIsPresentThenAreDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#default")).displayed();
    }

    @Test
    void whenElementIsPresentThenIsDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#default")).displayed();
    }

    @Test
    void whenElementIsNotDisplayedThenAreDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                            .untilEach($("#unvisible")).displayed();
                });
    }

    @Test
    void whenElementIsNotDisplayedThenIsDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#unvisible")).displayed();
                });
    }

    @Test
    void whenElementIsNotPresentThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#nonexistent")).not().displayed();
    }

    @Test
    void whenElementIsNotPresentThenAreDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                            .untilEach($("#nonexistent")).displayed();
                });
    }

    @Test
    void whenElementIsNotPresentThenAreEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                            .untilEach($("#nonexistent")).enabled();
                });
    }

    @Test
    void whenElementIsNotDisplayedThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#unvisible")).not().displayed();
    }

    @Test
    void whenElementIsNotDisplayedThenIsNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#unvisible")).not().displayed();
    }

    @Test
    void whenElementIsDisplayedThenAreNotDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#default"))
                            .not().displayed();
                });
    }

    @Test
    void whenElementIsDisplayedThenIsNotDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                            .untilEach($("#default")).not().displayed();
                });
    }

    @Test
    void whenElementIsEnabledThenAreEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).untilEach($("#default")).enabled();
    }

    @Test
    void whenElementIsEnabledThenIsEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#default")).enabled();
    }

    @Test
    void whenElementIsNotEnabledThenAreEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                            .untilEach($("#disabled")).enabled();
                });
    }

    @Test
    void whenElementIsNotEnabledThenIsEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#disabled")).enabled();

                });
    }

    @Test
    void whenElementIsSelectedThenIsSelectedReturnTrue() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#selected")).selected();
    }

    @Test
    void whenElementIsNotSelectedThenIsNotSelectedReturnTrue() {
        await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#non_selected")).not().selected();
    }

    @Test
    void whenElementIsNotSelectedThenAreSelectedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> await().atMost(MINIMAL_TIMEOUT, NANOSECONDS)
                        .untilEach($("#non_selected")).selected());
    }

    @Test
    void whenElementIsNotSelectedThenIsSelectedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    await().atMost(MINIMAL_TIMEOUT, NANOSECONDS).until($("#non_selected"))
                            .selected();
                });
    }

    @Test
    void checkPolling() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().pollingEvery(SECOND_AND_A_HALF_TIMEOUT, TimeUnit.MILLISECONDS)
                            .until($("#default")).text().equalTo("wait");
                });
    }

    @Test
    void checkIsAt() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).untilPage(new FluentPage()).isAt();
    }

    @Test
    void checkIsAtWithNullPage() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).untilPage().isAt();
                });
    }

    @Test
    void checkLoaded() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).untilPage().isLoaded();
    }

    @Test
    void checkPredicate() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(LESS_THAN_SECOND_TIMEOUT, TimeUnit.MILLISECONDS).untilPredicate(predicate -> true);
    }

    @Test
    void checkPredicateFail() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(SECOND_TIMEOUT).untilPredicate(input -> false);
                });
    }

    @Test
    void checkFunction() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).until(fluent -> true);
    }

    @Test
    void checkFunctionFail() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(SECOND_TIMEOUT).until(fluent -> false);
                });
    }

    @Test
    void checkSupplier() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(SECOND_TIMEOUT, TimeUnit.MILLISECONDS).until(() -> true);
    }

    @Test
    void checkSupplierFail() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(SECOND_TIMEOUT).until(() -> false);
                });
    }

    @Test
    void seleniumWaitIsAvailable() {
        FluentWait wait = await().getWait();
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


