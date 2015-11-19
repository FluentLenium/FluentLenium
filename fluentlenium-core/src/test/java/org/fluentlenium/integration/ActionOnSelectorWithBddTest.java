package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionOnSelectorWithBddTest extends LocalFluentCase {
    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#name").getValue()).contains("John");
        fill(findFirst("#name")).with("zzz");
        assertThat(findFirst("#name").getValue()).isEqualTo("zzz");
    }

    @Test
    public void checkFillSelectAction() {
        goTo(DEFAULT_URL);
        Select select = new Select(findFirst("#select").getElement());
        fillSelect("#select").withValue("value-1"); // by value
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 1");
        fillSelect("#select").withIndex(1); // by index
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 2");
        fillSelect("#select").withText("value 3"); // by text
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 3");
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
