package org.fluentlenium.assertj.integration.list;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListHasTextTest extends IntegrationTest {

    @Test
    public void testHasTextPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasText("A single line of text");
    }

    @Test
    public void testHasTextNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$("span")).hasText("John"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements contains text: John."
                        + " Actual texts found: ");
    }

    @Test
    public void testHasTextMatchingPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasTextMatching("A single line? of text");
    }

    @Test
    public void testHasTextMatchingNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$("span")).hasTextMatching("Jo?hn"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements contains text matching: Jo?hn."
                        + " Actual texts found: ");
    }

    @Test
    public void testHasNotTextPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasNotText("John");
    }

    @Test
    public void testHasNotTextNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$("span")).hasNotText("Paul"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("At least one selected elements contains text: Paul."
                        + " Actual texts found:");
    }

}
