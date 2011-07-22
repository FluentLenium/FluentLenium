package fr.java.freelance.fluentlenium.filter;

import fr.java.freelance.fluentlenium.filter.localTest.LocalFluentTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SelectorTest extends LocalFluentTest {


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


}
