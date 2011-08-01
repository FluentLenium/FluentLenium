package fr.java.freelance.fluentlenium.integration;

import fr.java.freelance.fluentlenium.domain.FluentList;
import fr.java.freelance.fluentlenium.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ActionOnListWithBddTest extends LocalFluentCase {


    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        FluentList input = find("input");
        fill(input).with("zzz");
        assertThat(input.getValues()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        FluentList name = find("#name");
        assertThat(name.getValues()).contains("John");
        clear(name);
        assertThat(name.getValues()).contains("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        FluentList name = find("#linkToPage2");
        assertThat(title()).contains("Selenium");
        click(name);
        assertThat(title()).isEqualTo("Page 2");
    }


}
