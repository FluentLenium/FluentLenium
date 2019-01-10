package org.fluentlenium.assertj.integration.list;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

public class FluentListHasValueTest extends IntegrationTest {

    @Test
    public void testHasIdPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("[type=checkbox]")).hasValue("John");
    }

    @Test
    public void testHasIdNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$("[type=checkbox]")).hasValue("Johnny"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have value: Johnny");
    }
}
