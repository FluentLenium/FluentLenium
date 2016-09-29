package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StateOnElementTest extends IntegrationFluentTest {
    @Before
    public void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void checkIsEnabled() {
        assertThat($("input").first().enabled()).isTrue();
    }

    @Test
    public void checkIsDisplayed() {
        assertThat($("input").first().displayed()).isTrue();
    }

    @Test
    public void checkIsNotSelected() {
        assertThat($("input").first().selected()).isFalse();
    }

    @Test
    public void checkIsSelected() {
        assertThat($("#selected").first().selected()).isTrue();
    }

    @Test
    public void checkIsDisabled() {
        assertThat($("#disabled").first().selected()).isFalse();
    }

    @Test
    public void checkIsNonDisplay() {
        assertThat($("#non_display").first().displayed()).isFalse();
    }

    @Test
    public void checkDisabledIsNotEnabled() {
        assertThat($("#disabled").first().enabled()).isFalse();
    }

}
