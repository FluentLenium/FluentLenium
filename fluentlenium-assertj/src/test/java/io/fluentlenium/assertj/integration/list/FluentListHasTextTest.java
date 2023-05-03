package io.fluentlenium.assertj.integration.list;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentListHasTextTest extends IntegrationTest {

    @Test
    public void testHasTextPositive() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasText("A single line of text");
    }

    @Test
    public void testHasTextNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($("span")).hasText("John"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements contains text: John."
                        + " Actual texts found: ");
    }

    @Test
    public void testHasTextMatchingPositive() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasTextMatching("A single line? of text");
    }

    @Test
    public void testHasTextMatchingNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($("span")).hasTextMatching("Jo?hn"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements contains text matching: Jo?hn."
                        + " Actual texts found: ");
    }

    @Test
    public void testHasNotTextPositive() {
        goTo(DEFAULT_URL);
        assertThat($("span")).hasNotText("John");
    }

    @Test
    public void testHasNotTextNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($("span")).hasNotText("Paul"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("At least one selected elements contains text: Paul."
                        + " Actual texts found:");
    }

}
