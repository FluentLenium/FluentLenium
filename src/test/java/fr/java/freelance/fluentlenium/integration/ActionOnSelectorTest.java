package fr.java.freelance.fluentlenium.integration;

import fr.java.freelance.fluentlenium.integration.localTest.LocalFluentTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ActionOnSelectorTest extends LocalFluentTest {


    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        $("#name").first().text("zzz");
        assertThat($("#name").getValues()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").first().getValue()).isEqualTo("John");
        $("#name").first().clear();
        assertThat($("#name").first().getValue()).isEqualTo("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").first().click();
        assertThat(title()).isEqualTo("Page 2");
    }


    @Test
    public void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        assertThat($(".small").first().getText()).contains("Small 1");
    }
}
