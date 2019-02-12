package org.fluentlenium.assertj.integration.list;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListHasClassTest extends IntegrationTest {

    @Test
    public void testHasIdPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("#multiple-css-class")).hasClass("class1");
    }

    @Test
    public void testHasIdNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$("#multiple-css-class")).hasClass("cla"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have class: cla");
    }
}
