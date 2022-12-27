package io.fluentlenium.test.await;

import io.fluentlenium.core.filter.FilterConstructor;import io.fluentlenium.core.filter.MatcherConstructor;import net.jcip.annotations.NotThreadSafe;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.containingText;
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
@SuppressWarnings({"PMD.GodClass", "PMD.ExcessivePublicCount"})
class FluentLeniumFunctionalWaitTest extends IntegrationFluentTest {

    @BeforeEach
    void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    void checkAwaitIsPresent() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small")).present();
    }

    @Test
    void checkAwaitIsClickable() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small")).clickable();
    }

    @Test
    void checkAwaitDisabledIsClickableThrowTimeoutException() {
        assertThrows(TimeoutException.class,
                () -> await().atMost(1, NANOSECONDS)
                        .untilElement(() -> el("input[disabled]"))
                        .clickable());
    }

    @Test
    void checkAwaitHasSize() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find(".small")).size(3);
    }

    @Test
    void checkAwaitHasTextWithText() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small", FilterConstructor.withText("Small 1"))).text().equalTo("Small 1");
    }

    @Test
    void checkAwaitContainsNameWithName() {
        await().atMost(1, NANOSECONDS).untilElement(() -> el(".small", FilterConstructor.withName("name"))).name("name");
    }

    @Test
    void checkAwaitContainsNameWithClass() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el("span", FilterConstructor.withClass("small"))).name("name");
    }

    @Test
    void checkAwaitContainsNameWithClassRegex() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el("span", FilterConstructor.withClass().contains(MatcherConstructor.regex("smal?")))).name("name");
    }

    @Test
    public void checkAwaitContainsNameWithClassAndContainsWord() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el("span", FilterConstructor.withClass().containsWord("small"))).name("name");
    }

    @Test
    void checkAwaitContainsTextWithText() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small", FilterConstructor.withText("Small 1"), FilterConstructor.containingText("Small 1")));
    }

    @Test
    void checkUseCustomMessage() {
        try {
            await().withMessage("toto").atMost(1, NANOSECONDS)
                    .untilElement(() -> el(".small", FilterConstructor.withText("Small 1"))).text().contains("Small 21");
            fail();
        } catch (TimeoutException e) {
            assertThat(e.getMessage()).contains("toto");
        }
    }

    @Test
    void checkAwaitContainsText() {
        await().atMost(1, NANOSECONDS).untilElement(() -> el(".small")).text().contains("Small 1");
    }

    @Test
    void checkAwaitContainsIdWithId() {
        await().atMost(1, NANOSECONDS).untilElement(() -> el(".small", FilterConstructor.withId("id2"))).id("id2");
    }

    @Test
    void checkAwaitStartsWithName() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find(".small", FilterConstructor.withName().startsWith("name"))).size(2);
    }

    @Test
    void checkAwaitContainsIdWithIdContains() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find(".small", FilterConstructor.withId().contains("id"))).size(2);
    }

    @Test
    void checkAwaitHasText() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small")).text("Small 1");
    }

    @Test
    void checkAwaitContainsName() {
        await().atMost(1, NANOSECONDS).untilElement(() -> el(".small")).name("name");
    }

    @Test
    void checkAwaitContainsId() {
        await().atMost(1, NANOSECONDS)
                .untilElements(() -> find(".small")).id("id2");
    }

    @Test
    void checkAwaitContainsTextWithTextMatcher() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small", FilterConstructor.withText().contains("Small 1"))).present();
    }

    @Test
    void checkAwaitContainsTextWithTextContentMatcher() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small", FilterConstructor.withTextContent().contains("Small 1"))).present();
    }

    @Test
    void whenAElementIsNotPresentThenIsNotPresentReturnTrue() {
        await().atMost(1, NANOSECONDS)
                .untilElements(() -> find(".small", FilterConstructor.withText().contains("notPresent"))).not().present();
    }

    @Test
    void whenAElementIsPresentThenIsNotPresentThrowAnException() {
        assertThrows(TimeoutException.class,
                () -> await().atMost(1, NANOSECONDS)
                        .untilElement(() -> el(".small", FilterConstructor.withText().contains("Small 1")))
                        .not().present());
    }

    @Test
    void checkAwaitStartWithRegex() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find(".small", FilterConstructor.with("id").startsWith(MatcherConstructor.regex(".d"))))
                .size(2);
    }

    @Test
    void checkAwaitStartWithString() {
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find(".small", FilterConstructor.with("id")
                .startsWith("id"))).size(2);
    }

    @Test
    void checkAwaitNotStartWith() {
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find(".small", FilterConstructor.with("id")
                .notStartsWith("id"))).size(1);
    }

    @Test
    void checkAwaitNotStartWithRegex() {
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find(".small", FilterConstructor.with("id")
                .notStartsWith(MatcherConstructor.regex("id")))).size(1);
    }

    @Test
    void checkAwaitEndsWithRegex() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find(".small", FilterConstructor.with("id")
                        .endsWith(MatcherConstructor.regex("2")))).size(1);
    }

    @Test
    void checkAwaitNotEndsWith() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small", FilterConstructor.with("id")
                        .notEndsWith("2"))).id("id");
    }

    @Test
    void checkAwaitNotEndsWithRegex() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el(".small", FilterConstructor.with("id").notEndsWith(MatcherConstructor.regex("2")))).id("id");
    }

    @Test
    void checkAwaitNotContains() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find(".small", FilterConstructor.with("id").notContains("d"))).size(1);
    }

    @Test
    void checkAwaitNotContainsRegex() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find(".small", FilterConstructor.with("id")
                        .notContains(MatcherConstructor.regex("d")))).size(1);
    }

    @Test
    void checkAwaitEquals() {
        await().atMost(1, NANOSECONDS)
                .untilElements(() -> find(".small", FilterConstructor.with("id")
                        .notContains("d"))).size().equalTo(1);
    }

    @Test
    void checkAwaitNotEquals() {
        await().atMost(1, NANOSECONDS)
                .untilElements(() -> find(".small", FilterConstructor.with("id")
                        .notContains("d"))).size().not().equalTo(10);
    }

    @Test
    void checkAwaitLessThan() {
        await().atMost(1, NANOSECONDS)
                .untilElements(() -> find(".small", FilterConstructor.with("id")
                        .notContains("d"))).size().lessThan(4);
    }

    @Test
    void checkAwaitLessThanOrEquals() {
        await().atMost(1, NANOSECONDS)
                .untilElements(() -> find(".small", FilterConstructor.with("id")
                        .notContains("d"))).size().lessThanOrEqualTo(1);
    }

    @Test
    void checkAwaitGreaterThan() {
        await().atMost(1, NANOSECONDS)
                .untilElements(() -> find(".small", FilterConstructor.with("id")
                        .notContains("d"))).size().greaterThan(-1);
    }

    @Test
    void checkAwaitGreaterThanOrEquals() {
        await().atMost(1, NANOSECONDS)
                .untilElements(() -> find(".small", FilterConstructor.with("id")
                        .notContains("d"))).size().greaterThanOrEqualTo(1);
    }

    @Test
    void checkWithValue() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find("input", FilterConstructor.with("value")
                        .equalTo("John"))).size(4);
    }

    @Test
    void checkMultipleFilter() {
        await().atMost(1, NANOSECONDS)
                .untilEachElements(() -> find(".small", FilterConstructor.with("id")
                        .startsWith(MatcherConstructor.regex("id")), FilterConstructor.with("text").endsWith("2"))).size(1);
    }

    @Test
    void checkHasAttribute() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el("input")).attribute("value", "John");
    }

    @Test
    void checkHasAttributeWithOthersFilters() {
        await().atMost(1, NANOSECONDS)
                .untilElement(() -> el("input", FilterConstructor.with("value")
                        .equalTo("John"))).attribute("value", "John");
    }

    @Test
    void whenElementIsPresentThenAreDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#default")).displayed();
    }

    @Test
    void whenElementIsPresentThenIsDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(() -> find("#default")).displayed();
    }

    @Test
    void whenElementIsNotDisplayedThenAreDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#unvisible"))
                            .displayed();
                });
    }

    @Test
    void whenElementIsNotDisplayedThenIsDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilElements(() -> find("#unvisible"))
                            .displayed();

                });
    }

    @Test
    void whenElementIsNotPresentThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#nonexistent")).not().displayed();
    }

    @Test
    void whenElementIsNotPresentThenIsNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#nonexistent")).not().displayed();
    }

    @Test
    void whenElementIsNotDisplayedThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#unvisible")).not().displayed();
    }

    @Test
    void whenElementIsNotDisplayedThenIsNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#unvisible")).not().displayed();
    }

    @Test
    void whenElementIsDisplayedThenAreNotDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#default"))
                            .not().displayed();
                });
    }

    @Test
    void whenElementIsDisplayedThenIsNotDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilElements(() -> find("#default"))
                            .not().displayed();
                });
    }

    @Test
    void whenElementIsEnabledThenAreEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#default")).enabled();
    }

    @Test
    void whenElementIsEnabledThenIsEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(() -> find("#default")).enabled();
    }

    @Test
    void whenElementIsNotEnabledThenAreEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilEachElements(() -> find("#disabled"))
                            .enabled();
                });
    }

    @Test
    void whenElementIsNotEnabledThenIsEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilElements(() -> find("#disabled"))
                            .enabled();
                });
    }

    @Test
    void whenElementIsNotDisplayedThenIsPresentReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(() -> find("#invisible")).present();
    }

    @Test
    void checkPolling() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().pollingEvery(1500, TimeUnit.MILLISECONDS)
                            .untilElements(() -> find("#default"))
                            .text().equalTo("wait");
                });

    }
}


