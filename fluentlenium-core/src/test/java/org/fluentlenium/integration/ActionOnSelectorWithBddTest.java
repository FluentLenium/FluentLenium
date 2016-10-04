package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionOnSelectorWithBddTest extends IntegrationFluentTest {
    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        assertThat(el("#name").value()).contains("John");
        el("#name").fill().with("zzz");
        assertThat(el("#name").value()).isEqualTo("zzz");
    }

    @Test
    public void checkFillSelectAction() {
        goTo(DEFAULT_URL);
        final Select select = new Select(el("#select").getElement());
        $("#select").fillSelect().withValue("value-1"); // by value
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 1");
        $("#select").fillSelect().withIndex(1); // by index
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 2");
        $("#select").fillSelect().withText("value 3"); // by text
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 3");
    }

    @Test
    public void checkFillSelectActionOnSelectElement() {
        goTo(DEFAULT_URL);
        final FluentWebElement element = el("#select");
        final Select select = new Select(element.getElement());
        element.fillSelect().withValue("value-1"); // by value
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 1");
        element.fillSelect().withIndex(1); // by index
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 2");
        element.fillSelect().withText("value 3"); // by text
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 3");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat(el("#name").value()).contains("John");
        el("#name").clear();
        assertThat($("#name").first().value()).isEqualTo("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        el("#linkToPage2").click();
        assertThat(window().title()).isEqualTo("Page 2");
    }
}
