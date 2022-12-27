package io.fluentlenium.test.actions.click;

import static org.assertj.core.api.Assertions.assertThat;

import io.fluentlenium.test.IntegrationFluentTest;

abstract class AbstractClicksTest extends IntegrationFluentTest {

    void checkDoubleClick() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        $("#linkToPage2").first().mouse().doubleClick();
        assertThat(window().title()).isEqualTo("Page 2");
    }

    void checkMouseOver() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        assertThat($("#id3").first().text()).isEqualTo("This text should change on MouseOver");
        $("#mouseover").first().mouse().moveToElement();
        assertThat($("#id3").first().text()).isEqualTo("abc");
    }
}
