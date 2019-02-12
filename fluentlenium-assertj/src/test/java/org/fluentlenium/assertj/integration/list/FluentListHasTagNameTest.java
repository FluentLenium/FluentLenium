package org.fluentlenium.assertj.integration.list;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

public class FluentListHasTagNameTest extends IntegrationTest {

    @Test
    public void testHasNamePositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$(".small")).hasTagName("span");
    }

    @Test
    public void testHasNameNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$(".small")).hasTagName("div"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have tag: div");
    }
}
