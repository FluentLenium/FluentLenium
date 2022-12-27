package io.fluentlenium.assertj.integration.element;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementSelectedTest extends IntegrationTest {

    @Test
    public void testIsSelectedPositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#selected")).isSelected();
    }

    @Test
    public void testIsSelectedNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#disabled")).isSelected())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but not selected");
    }

    @Test
    public void testIsSelectedNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#nonexisting")).isSelected())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }

    @Test
    public void testIsNotSelectedPositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#disabled")).isNotSelected();
    }

    @Test
    public void testIsNotSelectedNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#selected")).isNotSelected())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is present but selected");
    }

    @Test
    public void testIsNotSelectedNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#nonexisting")).isNotSelected())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Element in assertion is not present");
    }

}
