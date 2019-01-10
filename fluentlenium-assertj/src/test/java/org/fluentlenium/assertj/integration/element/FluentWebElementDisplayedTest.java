package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementDisplayedTest extends IntegrationTest {

    @Test
    public void testIsDisplayedPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#disabled")).isDisplayed();
    }

    @Test
    public void testIsDisplayedNegative() {
        standalone.goTo(DEFAULT_URL);
        standalone.executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThatThrownBy(() -> assertThat(standalone.el("#disabled")).isDisplayed())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but not displayed");
    }

    @Test
    public void testIsDisplayedNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#nonexisting")).isDisplayed())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("is not present");
    }

    @Test
    public void testIsNotDisplayedPositive() {
        standalone.goTo(DEFAULT_URL);
        standalone.executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThat(standalone.el("#disabled")).isNotDisplayed();
    }

    @Test
    public void testIsNotDisplayedNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#disabled")).isNotDisplayed())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but displayed");
    }

    @Test
    public void testIsNotDisplayedNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#nonexisting")).isNotDisplayed())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("is not present");
    }
}
