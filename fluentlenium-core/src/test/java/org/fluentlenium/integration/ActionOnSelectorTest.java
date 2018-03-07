package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionOnSelectorTest extends IntegrationFluentTest {

    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").values()).contains("John");
        $("#name").first().write("zzz");
        assertThat($("#name").values()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").first().value()).isEqualTo("John");
        $("#name").first().clear();
        assertThat($("#name").first().value()).isEqualTo("");
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
        assertThat(window().title()).contains("Selenium");
        $("#linkToPage2").first().click();
        assertThat(window().title()).isEqualTo("Page 2");
    }

    @Test
    public void checkClickActionWrongSelector() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        Assertions.assertThatThrownBy(() -> $("#BLUB").click()).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").values()).contains("John");
        assertThat($(".small").first().text()).contains("Small 1");
    }
}
