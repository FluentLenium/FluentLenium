package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionOnSelectorTest extends LocalFluentCase {


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
    public void checkFillOnDateAction() {
        goTo(DEFAULT_URL);
        $("#date").first().fill().with("01/01/1988");
    }

    @Test
    public void checkClearOnDateAction() {
        goTo(DEFAULT_URL);
        $("#date").clear();
    }

    @Test
    public void checkFillOnTimeAction() {
        goTo(DEFAULT_URL);
        $("#time").first().fill().with("01/01/1988");
    }

    @Test
    public void checkClearOnTimeAction() {
        goTo(DEFAULT_URL);
        $("#time").clear();
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").first().click();
        assertThat(title()).isEqualTo("Page 2");
    }

    @Test
    public void checkClickActionWrongSelector() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        try {
            click("#BLUB");
            org.junit.Assert.fail("NoSuchElementException should have been thrown!");
        } catch (NoSuchElementException nsee) {
            assertThat(nsee.getMessage()).startsWith("No Element found");
        }
    }

    @Test
    public void checkDoubleClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").first().doubleClick();
        assertThat(title()).isEqualTo("Page 2");
    }

    @Test
    public void checkMouseOverAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        assertThat($("#id3").getText()).isEqualTo("This text should change on MouseOver");
        $("#mouseover").first().mouseOver();
        assertThat($("#id3").getText()).isEqualTo("abc");
    }

    @Test
    public void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        assertThat($(".small").first().getText()).contains("Small 1");
    }
}
