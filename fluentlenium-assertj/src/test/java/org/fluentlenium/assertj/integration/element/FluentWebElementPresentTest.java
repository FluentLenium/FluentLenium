package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementPresentTest extends IntegrationTest {

    @Test
    public void testIsPresentPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#disabled")).isPresent();
    }

    @Test
    public void testIsPresentNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#notPresent")).isPresent())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }

    @Test
    public void testIsNotPresentPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#notPresent")).isNotPresent();
    }

    @Test
    public void testIsNotDisplayedNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#disabled")).isNotPresent())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present");
    }

}
