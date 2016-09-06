package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.with;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;

public class FluentSelectorTest extends IntegrationFluentTest {


    @Test
    public void checkWithNameCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    public void checkWithIdCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withId("id"))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherCssPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().contains(regex("na?me[0-9]*"))).getNames()).contains("name", "name2");
    }

    @Test
    public void checkWithNameMatcherCssNotContainPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().notContains(regex("na?me[0-9]*"))).getNames()).hasSize(1);
    }

    @Test
    public void checkWithNameEqualMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().equalTo("name"))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherNotContainsCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName().notContains("toto"))).hasSize(3);
    }


    @Test
    public void checkCustomSelectAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated").equalTo("true")).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeWithRegex() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated").contains(regex("t?ru?"))).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeIfText() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("TEXT").equalTo("Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkCustomSelectAttributeIfTextIsInLowerCase() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("text").equalTo("Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().startsWith(regex("na?"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().startsWith(regex("am")))).hasSize(0);
    }


    @Test
    public void checkEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().endsWith(regex("na[me]*"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().endsWith(regex("am?")))).hasSize(0);
    }


    @Test
    public void checkNotStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().notStartsWith("na")).getIds()).contains("oneline");
    }

    @Test
    public void checkNotStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().notStartsWith(regex("na?"))).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkNotEndStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().notEndsWith("na")).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkNotEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName().notEndsWith(regex("na?"))).first().getId()).isEqualTo("oneline");
    }

    @Test
    public void checkWithClassCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#id", withClass("small"))).hasSize(1);
    }

    @Test
    public void checkWithClassEqualMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#id", withClass().equalTo("small"))).hasSize(1);
    }

    @Test
    public void checkWithClassRegexMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#id", withClass().contains(regex("smal?")))).hasSize(1);
    }

    @Test
    public void checkMultipleClass2Selector() {
        goTo(DEFAULT_URL);
        assertThat($("button", withClass().equalTo("class1 class2 class3"))).hasSize(1);
    }

    @Test
    public void checkMultipleClassContainsWordsSelector() {
        goTo(DEFAULT_URL);
        assertThat($("button", withClass().containsWord("class1"), withClass().containsWord("class2"))).hasSize(1);
    }

    @Test
    public void checkClassContainsSelector() {
        goTo(DEFAULT_URL);
        assertThat($("button", withClass().contains("class"), withClass().containsWord("class2"))).hasSize(1);
    }

    @Test
    public void checkClassContainsWordSelector() {
        goTo(DEFAULT_URL);
        assertThat($("button", withClass().containsWord("class"))).hasSize(0);
    }
}

