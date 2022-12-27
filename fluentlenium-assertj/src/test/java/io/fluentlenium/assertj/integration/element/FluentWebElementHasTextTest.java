package io.fluentlenium.assertj.integration.element;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasTextTest extends IntegrationTest {

    @Test
    public void testHasTextPostive() {
        goTo(DEFAULT_URL);
        assertThat(el("#location")).hasText("Pharmacy");
    }

    @Test
    public void testHasTextNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#location")).hasText("Drugstore"))
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "The element does not contain the text: Drugstore. Actual text found : Pharmacy");
    }

    @Test
    public void testHasNotTextPostive() {
        goTo(DEFAULT_URL);
        assertThat(el("#location")).hasNotText("Drugstore");
    }

    @Test
    public void testHasNotTextNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#location")).hasNotText("Pharmacy"))
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "The element contains the text: Pharmacy");
    }

    @Test
    public void testHasTextMatchingPositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#location")).hasTextMatching("Pha\\w+cy");
    }

    @Test
    public void testHasTextMatchingNegative() {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"location\").innerHTML=\"Pha rmacy\";");
        assertThatThrownBy(() -> assertThat(el("#location")).hasTextMatching("Pha\\w+cy"))
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "The element does not match the regex: Pha\\w+cy. Actual text found : Pha rmacy");
    }

}
