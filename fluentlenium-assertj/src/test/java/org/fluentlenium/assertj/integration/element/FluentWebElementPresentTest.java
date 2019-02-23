package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementPresentTest extends IntegrationTest {

    @Test
    public void testIsPresentPositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isPresent();
    }

    @Test
    public void testIsPresentNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#notPresent")).isPresent())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }

    @Test
    public void testIsNotPresentPositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#notPresent")).isNotPresent();
    }

    @Test
    public void testIsNotPresentNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#disabled")).isNotPresent())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present");
    }

}
