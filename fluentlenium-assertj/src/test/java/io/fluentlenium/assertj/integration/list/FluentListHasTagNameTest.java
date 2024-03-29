package io.fluentlenium.assertj.integration.list;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentListHasTagNameTest extends IntegrationTest {

    @Test
    public void testHasNamePositive() {
        goTo(DEFAULT_URL);
        assertThat($(".small")).hasTagName("span");
    }

    @Test
    public void testHasNameNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($(".small")).hasTagName("div"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have tag: div");
    }
}
