package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementClickableTest extends IntegrationTest {

    @Test
    public void testIsClickablePositive() {
        goTo(DEFAULT_URL);
        assertThat(el("select")).isClickable();
    }

    @Test
    public void testIsClickableNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#disabled")).isClickable())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but not clickable");
    }

    @Test
    public void testIsClickableNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#nonexisting")).isClickable())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }

    @Test
    public void testIsNotClickablePositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isNotClickable();
    }

    @Test
    public void testIsNotClickableNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("select")).isNotClickable())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but clickable");
    }

    @Test
    public void testIsNotClickableNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#nonexisting")).isNotClickable())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }

}
