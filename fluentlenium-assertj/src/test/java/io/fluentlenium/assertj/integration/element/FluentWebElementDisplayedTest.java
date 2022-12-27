package io.fluentlenium.assertj.integration.element;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentWebElementDisplayedTest extends IntegrationTest {

    @Test
    public void testIsDisplayedPositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isDisplayed();
    }

    @Test
    public void testIsDisplayedNegative() {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThatThrownBy(() -> assertThat(el("#disabled")).isDisplayed())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but not displayed");
    }

    @Test
    public void testIsDisplayedNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#nonexisting")).isDisplayed())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }

    @Test
    public void testIsNotDisplayedPositive() {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThat(el("#disabled")).isNotDisplayed();
    }

    @Test
    public void testIsNotDisplayedNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#disabled")).isNotDisplayed())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but displayed");
    }

    @Test
    public void testIsNotDisplayedNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#nonexisting")).isNotDisplayed())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }
}
