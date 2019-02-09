package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementEnabledTest extends IntegrationTest {

    @Test
    public void testIsEnabledPostive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#name")).isEnabled();
    }

    @Test
    public void testIsEnabledNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#disabled")).isEnabled())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but not enabled");
    }

    @Test
    public void testIsEnabledNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#nonexisting")).isEnabled())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Element in assertion is not present");
    }

    @Test
    public void testIsNotEnabledPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#disabled")).isNotEnabled();
    }

    @Test
    public void testIsNotEnabledNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#name")).isNotEnabled())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but enabled");
    }

    @Test
    public void testIsNotEnabledNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#nonexisting")).isNotEnabled())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }
}
