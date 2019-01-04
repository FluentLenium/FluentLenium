package org.fluentlenium.assertj.integration.fluentwebelement;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasIdTest extends IntegrationTest {

    @Test
    public void testHasIdPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el(".textclass")).hasId("oneline");
    }

    @Test
    public void testHasIdNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el(".textclass")).hasId("wrongid"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(
                        "The element does not have the id: wrongid. Actual id found : oneline");
    }

}
