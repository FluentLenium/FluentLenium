package io.fluentlenium.test.actions.onlist;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActionOnListWithBddTest extends IntegrationFluentTest {

    @SuppressWarnings("unchecked")
    @Test
    void checkFillAction() {
        goTo(DEFAULT_URL);
        FluentList input = find("input[type=text]");
        input.fill().with("zzz");
        assertThat(input.values()).contains("zzz");
    }

    @Test
    void checkFillActionOnElement() {
        goTo(DEFAULT_URL);
        FluentWebElement input = find("input").first();
        input.fill().with("zzz");
        assertThat(input.value()).contains("zzz");
    }

    @SuppressWarnings("unchecked")
    @Test
    void checkClearAction() {
        goTo(DEFAULT_URL);
        FluentList name = find("#name");
        assertThat(name.values()).contains("John");
        name.clear();
        assertThat(name.values()).contains("");
    }

    @Test
    void checkClickAction() {
        goTo(DEFAULT_URL);
        FluentList name = find("#linkToPage2");
        assertThat(window().title()).contains("Selenium");
        name.click();
        assertThat(window().title()).isEqualTo("Page 2");
    }

}
