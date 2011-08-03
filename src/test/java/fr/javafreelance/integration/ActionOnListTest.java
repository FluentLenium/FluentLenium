package fr.javafreelance.integration;

import fr.javafreelance.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ActionOnListTest extends LocalFluentCase {

    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        $("input").text("zzz");
        assertThat($("input").getValues()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        $("#name").clear();
        assertThat($("#name").getValues()).contains("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").click();
        assertThat(title()).isEqualTo("Page 2");
    }


    @Test
    public void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        assertThat($(".small").getTexts()).contains("Small 1", "Small 2", "Small 3");
    }
}
