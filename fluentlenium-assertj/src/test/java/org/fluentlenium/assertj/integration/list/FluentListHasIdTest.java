package org.fluentlenium.assertj.integration.list;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

public class FluentListHasIdTest extends IntegrationTest {

    @Test
    public void testHasIdPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$(".small")).hasId("id2");
    }

    @Test
    public void testHasIdNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$(".small")).hasId("id3"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have id: id3");
    }
}
