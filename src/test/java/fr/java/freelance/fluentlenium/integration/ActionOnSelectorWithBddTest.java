package fr.java.freelance.fluentlenium.integration;

import fr.java.freelance.fluentlenium.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ActionOnSelectorWithBddTest extends LocalFluentCase {
    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#name").getValue()).contains("John");
        fill(findFirst("#name")).with("zzz");
        assertThat(findFirst("#name").getValue()).isEqualTo("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#name").getValue()).contains("John");
        clear(findFirst("#name"));
        assertThat($("#name").first().getValue()).isEqualTo("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        click(findFirst("#linkToPage2"));
        assertThat(title()).isEqualTo("Page 2");
    }
}
