package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasTextTest extends IntegrationTest {

    @Test
    public void testHasTextPostive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#location")).hasText("Pharmacy");
    }

    @Test
    public void testHasTextNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#location")).hasText("Drugstore"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(
                        "The element does not contain the text: Drugstore. Actual text found : Pharmacy");
    }

    @Test
    public void testHasNotTextPostive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#location")).hasNotText("Drugstore");
    }

    @Test
    public void testHasNotTextNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#location")).hasNotText("Pharmacy"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(
                        "The element contain the text: Pharmacy");
    }

    @Test
    public void testHasTextMatchingPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#location")).hasTextMatching("Pha\\w+cy");
    }

    @Test
    public void testHasTextMatchingNegative() {
        standalone.goTo(DEFAULT_URL);
        standalone.executeScript("document.getElementById(\"location\").innerHTML=\"Pha rmacy\";");
        assertThatThrownBy(() -> assertThat(standalone.el("#location")).hasTextMatching("Pha\\w+cy"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(
                        "The element does not match the regex: Pha\\w+cy. Actual text found : Pha rmacy");
    }

}
