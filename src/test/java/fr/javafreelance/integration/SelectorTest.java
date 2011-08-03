package fr.javafreelance.integration;

import fr.javafreelance.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static fr.javafreelance.fluentlenium.filter.FilterConstructor.*;
import static org.fest.assertions.Assertions.assertThat;

public class SelectorTest extends LocalFluentCase {


    @Test
    public void checkTagSelector() {
        goTo(DEFAULT_URL);
        assertThat($("h1")).hasSize(1);
    }

    @Test
    public void checkIdSelector() {
        goTo(DEFAULT_URL);
        assertThat($("#oneline")).hasSize(1);
    }

    @Test
    public void checkCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small")).hasSize(3);
    }

    @Test
    public void checkWithNameCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(contains("name")))).hasSize(2);
    }

    @Test
    public void checkWithNameMatcherCssPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(contains(regex("na?me[0-9]*")))).getNames()).contains("name", "name2");
    }

    @Test
    public void checkWithNameMatcherCssNotContainPatternSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(notContains(regex("na?me[0-9]*")))).getNames()).hasSize(1);
    }

    @Test
    public void checkWithNameEqualMatcherCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(equal("name")))).hasSize(1);
    }

    @Test
    public void checkWithNameMatcherNotContainsCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withName(notContains("toto")))).hasSize(3);
    }

    @Test
    public void checkWithIdCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withId("id"))).hasSize(1);
    }

    @Test
    public void checkWithTextCssSelector() {
        goTo(DEFAULT_URL);
        assertThat($(".small", withText("Small 2"))).hasSize(1);
    }

    @Test
    public void checkSelectAttributeAction() {
        goTo(DEFAULT_URL);
        assertThat($(".small", 2).getText()).isEqualTo("Small 3");
    }


    @Test
    public void checkCustomSelectAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated", "true")).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeWithRegex() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("generated", contains(regex("t?ru?")))).getTexts()).contains("Test custom attribute");
    }

    @Test
    public void checkCustomSelectAttributeIfText() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("TEXT", "Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkCustomSelectAttributeIfTextIsInLowerCase() {
        goTo(DEFAULT_URL);
        assertThat($("span", with("text", "Pharmacy")).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith("na"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith(regex("na?")))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkStartAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(startsWith(regex("am"))))).hasSize(0);
    }

    @Test
    public void checkEndAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith("me")))).hasSize(1);
        assertThat($("span", withName(endsWith("name"))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttributeMatcher() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith(regex("na[me]*")))).first().getTagName()).isEqualTo("span");
    }

    @Test
    public void checkEndAttributeMatcherNotFind() {
        goTo(DEFAULT_URL);
        assertThat($("span", withName(endsWith(regex("am?"))))).hasSize(0);
    }


}
