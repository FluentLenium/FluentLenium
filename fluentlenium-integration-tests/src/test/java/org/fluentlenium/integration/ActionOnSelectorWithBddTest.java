package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;

class ActionOnSelectorWithBddTest extends IntegrationFluentTest {
    @Test
    void checkFillAction() {
        goTo(DEFAULT_URL);
        assertThat(el("#name").value()).contains("John");
        el("#name").fill().with("zzz");
        assertThat(el("#name").value()).isEqualTo("zzz");
    }

    @Test
    void checkFillSelectAction() {
        goTo(DEFAULT_URL);
        Select select = new Select(el("#select").getElement());
        $("#select").fillSelect().withValue("value-1"); // by value
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 1");
        $("#select").fillSelect().withIndex(1); // by index
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 2");
        $("#select").fillSelect().withText("value 3"); // by text
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 3");
    }

    @Test
    void checkFillSelectActionOnSelectElement() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el("#select");
        Select select = new Select(element.getElement());
        element.fillSelect().withValue("value-1"); // by value
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 1");
        element.fillSelect().withIndex(1); // by index
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 2");
        element.fillSelect().withText("value 3"); // by text
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 3");
    }

    @Test
    void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat(el("#name").value()).contains("John");
        el("#name").clear();
        assertThat($("#name").first().value()).isEqualTo("");
    }

    @Test
    void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        el("#linkToPage2").click();
        assertThat(window().title()).isEqualTo("Page 2");
    }
}
