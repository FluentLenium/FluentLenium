package org.fluentlenium.assertj.integration.fluentwebelement;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementClickableTest extends IntegrationTest {

    @Test
    public void testIsClickablePositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("select")).isClickable();
    }

    @Test
    public void testIsClickableNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#disabled")).isClickable())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Element in assertion is present but not clickable");
    }

    @Test
    public void testIsClickableNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#nonexisting")).isClickable())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("is not present");
    }

    @Test
    public void testIsNotClickablePositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#disabled")).isNotClickable();
    }

    @Test
    public void testIsNotClickableNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("select")).isNotClickable())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Element in assertion is present but clickable");
    }

    @Test
    public void testIsNotClickableNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#nonexisting")).isNotClickable())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("is not present");
    }

}
