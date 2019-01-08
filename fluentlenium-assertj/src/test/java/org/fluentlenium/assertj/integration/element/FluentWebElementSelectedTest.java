package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementSelectedTest extends IntegrationTest {

    @Test
    public void testIsSelectedPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#selected")).isSelected();
    }

    @Test
    public void testIsSelectedNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#disabled")).isSelected())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Element in assertion is present but not selected");
    }

    @Test
    public void testIsSelectedNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#nonexisting")).isSelected())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("is not present");
    }

    @Test
    public void testIsNotSelectedPositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#disabled")).isNotSelected();
    }

    @Test
    public void testIsNotSelectedNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#selected")).isNotSelected())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Element in assertion is present but selected");
    }

    @Test
    public void testIsNotSelectedNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#nonexisting")).isNotSelected())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("is not present");
    }

}
