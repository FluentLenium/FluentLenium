package org.fluentlenium.assertj.integration.list;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListHasClassTest extends IntegrationTest {

    @Test
    public void testHasIdPositive() {
        goTo(DEFAULT_URL);
        assertThat($("#multiple-css-class")).hasClass("class1");
    }

    @Test
    public void testHasIdNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($("#multiple-css-class")).hasClass("cla"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have class: cla");
    }
}
