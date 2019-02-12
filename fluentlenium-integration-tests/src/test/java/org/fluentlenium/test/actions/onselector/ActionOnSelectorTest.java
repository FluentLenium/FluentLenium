package org.fluentlenium.test.actions.onselector;

import org.assertj.core.api.Assertions;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class ActionOnSelectorTest extends IntegrationFluentTest {

    @Test
    void checkFillAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").values()).contains("John");
        $("#name").first().write("zzz");
        assertThat($("#name").values()).contains("zzz");
    }

    @Test
    void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").first().value()).isEqualTo("John");
        $("#name").first().clear();
        assertThat($("#name").first().value()).isEqualTo("");
    }

    @Test
    void checkFillOnDateAction() {
        goTo(DEFAULT_URL);
        $("#date").first().fill().with("01/01/1988");
    }

    @Test
    void checkClearOnDateAction() {
        goTo(DEFAULT_URL);
        $("#date").clear();
    }

    @Test
    void checkFillOnTimeAction() {
        goTo(DEFAULT_URL);
        $("#time").first().fill().with("01/01/1988");
    }

    @Test
    void checkClearOnTimeAction() {
        goTo(DEFAULT_URL);
        $("#time").clear();
    }

    @Test
    void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        $("#linkToPage2").first().click();
        assertThat(window().title()).isEqualTo("Page 2");
    }

    @Test
    void checkClickActionWrongSelector() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        Assertions.assertThatThrownBy(() -> $("#BLUB").click()).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").values()).contains("John");
        assertThat($(".small").first().text()).contains("Small 1");
    }
}
