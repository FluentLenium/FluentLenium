package io.fluentlenium.test.fluentwebelement;

import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateOnElementTest extends IntegrationFluentTest {

    @BeforeEach
    void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    void checkIsEnabled() {
        assertThat($("input").first().enabled()).isTrue();
    }

    @Test
    void checkIsDisplayed() {
        assertThat($("input").first().displayed()).isTrue();
    }

    @Test
    void checkIsNotSelected() {
        assertThat($("input").first().selected()).isFalse();
    }

    @Test
    void checkIsSelected() {
        assertThat($("#selected").first().selected()).isTrue();
    }

    @Test
    void checkIsDisabled() {
        assertThat($("#disabled").first().selected()).isFalse();
    }

    @Test
    void checkIsNonDisplay() {
        assertThat($("#non_display").first().displayed()).isFalse();
    }

    @Test
    void checkDisabledIsNotEnabled() {
        assertThat($("#disabled").first().enabled()).isFalse();
    }

}
