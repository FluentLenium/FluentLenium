package io.fluentlenium.test.findby;

import io.fluentlenium.core.filter.FilterConstructor;
import io.fluentlenium.core.filter.MatcherConstructor;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FluentSelectorTest extends IntegrationFluentTest {

    @Test
    void checkWithNameCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

    @Test
    void checkWithIdCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withId("id"))).hasSize(1);
    }

    @Test
    void checkWithNameMatcherCssPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName().contains(MatcherConstructor.regex("na?me[0-9]*"))).names()).contains("name", "name2");
    }

    @Test
    void checkWithNameMatcherCssNotContainPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName().notContains(MatcherConstructor.regex("na?me[0-9]*"))).names()).hasSize(1);
    }

    @Test
    void checkWithNameEqualMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName().equalTo("name"))).hasSize(1);
    }

    @Test
    void checkWithNameMatcherNotContainsCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName().notContains("toto"))).hasSize(3);
    }

    @Test
    void checkCustomSelectAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.with("generated").equalTo("true")).texts()).contains("Test custom attribute");
    }

    @Test
    void checkCustomSelectAttributeWithRegex() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.with("generated").contains(MatcherConstructor.regex("t?ru?"))).texts()).contains("Test custom attribute");
    }

    @Test
    void checkCustomSelectAttributeIfText() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.with("TEXT").equalTo("Pharmacy")).first().tagName()).isEqualTo("span");
    }

    @Test
    void checkCustomSelectAttributeIfTextIsInLowerCase() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.with("text").equalTo("Pharmacy")).first().tagName()).isEqualTo("span");
    }

    @Test
    void checkStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.withName().startsWith(MatcherConstructor.regex("na?"))).first().tagName()).isEqualTo("span");
    }

    @Test
    void checkStartAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.withName().startsWith(MatcherConstructor.regex("am")))).isEmpty();
    }

    @Test
    void checkEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.withName().endsWith(MatcherConstructor.regex("na[me]*"))).first().tagName()).isEqualTo("span");
    }

    @Test
    void checkEndAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.withName().endsWith(MatcherConstructor.regex("am?")))).isEmpty();
    }

    @Test
    void checkNotStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.withName().notStartsWith("na")).ids()).contains("oneline");
    }

    @Test
    void checkPredicate() {
        goTo(DEFAULT_URL);
        assertThat($("span",
                FilterConstructor.withPredicate(input -> input.id() != null && !input.id().startsWith("na"))).ids()).contains("oneline");
    }

    @Test
    void checkNotStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.withName().notStartsWith(MatcherConstructor.regex("na?"))).first().id()).isEqualTo("oneline");
    }

    @Test
    void checkNotEndStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.withName().notEndsWith("na")).first().id()).isEqualTo("oneline");
    }

    @Test
    void checkNotEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", FilterConstructor.withName().notEndsWith(MatcherConstructor.regex("na?"))).first().id()).isEqualTo("oneline");
    }

    @Test
    void checkWithClassCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#id", FilterConstructor.withClass("small"))).hasSize(1);
    }

    @Test
    void checkWithClassEqualMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#id", FilterConstructor.withClass().equalTo("small"))).hasSize(1);
    }

    @Test
    void checkWithClassRegexMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#id", FilterConstructor.withClass().contains(MatcherConstructor.regex("smal?")))).hasSize(1);
    }

    @Test
    void checkMultipleClass2Selector() {
        goTo(DEFAULT_URL);
        assertThat($("button", FilterConstructor.withClass().equalTo("class1 class2 class3"))).hasSize(1);
    }

    @Test
    void checkMultipleClassContainsWordsSelector() {
        goTo(DEFAULT_URL);
        assertThat($("button", FilterConstructor.withClass().containsWord("class1"), FilterConstructor.withClass().containsWord("class2"))).hasSize(1);
    }

    @Test
    void checkClassContainsSelector() {
        goTo(DEFAULT_URL);
        assertThat($("button", FilterConstructor.withClass().contains("class"), FilterConstructor.withClass().containsWord("class2"))).hasSize(1);
    }

    @Test
    void checkClassContainsWordSelector() {
        goTo(DEFAULT_URL);
        assertThat($("button", FilterConstructor.withClass().containsWord("class"))).isEmpty();
    }
}

