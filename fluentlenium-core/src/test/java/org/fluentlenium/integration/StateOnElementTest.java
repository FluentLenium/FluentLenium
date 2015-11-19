package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StateOnElementTest extends LocalFluentCase {
    @Before
    public void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void checkIsEnabled() {
        assertThat($("input").first().isEnabled()).isTrue();
    }

    @Test
    public void checkIsDisplayed() {
        assertThat($("input").first().isDisplayed()).isTrue();
    }

    @Test
    public void checkIsNotSelected() {
        assertThat($("input").first().isSelected()).isFalse();
    }

    @Test
    public void checkIsSelected() {
        assertThat($("#selected").first().isSelected()).isTrue();
    }

    @Test
    public void checkIsDisabled() {
        assertThat($("#disabled").first().isSelected()).isFalse();
    }

    @Test
    public void checkIsNonDisplay() {
        assertThat($("#non_display").first().isDisplayed()).isFalse();
    }

    @Test
    public void checkDisabledIsNotEnabled() {
        assertThat($("#disabled").first().isEnabled()).isFalse();
    }

}
