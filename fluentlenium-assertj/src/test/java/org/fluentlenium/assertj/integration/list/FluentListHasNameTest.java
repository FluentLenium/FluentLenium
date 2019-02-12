package org.fluentlenium.assertj.integration.list;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

public class FluentListHasNameTest extends IntegrationTest {

    @Test
    public void testHasNamePositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$(".small")).hasName("name2");
    }

    @Test
    public void testHasNameNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$(".small")).hasName("name3"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have name: name3");
    }
}
