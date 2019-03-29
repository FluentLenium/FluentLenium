package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementEnabledTest extends IntegrationTest {

    @Test
    public void testIsEnabledPostive() {
        goTo(DEFAULT_URL);
        assertThat(el("#name")).isEnabled();
    }

    @Test
    public void testIsEnabledNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#disabled")).isEnabled())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but not enabled");
    }

    @Test
    public void testIsEnabledNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#nonexisting")).isEnabled())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Element in assertion is not present");
    }

    @Test
    public void testIsNotEnabledPositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isNotEnabled();
    }

    @Test
    public void testIsNotEnabledNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#name")).isNotEnabled())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but enabled");
    }

    @Test
    public void testIsNotEnabledNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#nonexisting")).isNotEnabled())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }
}
