package io.fluentlenium.assertj.integration.element;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentWebElementHasTagNameTest extends IntegrationTest {

    @Test
    public void testHasTagNamePositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#id")).hasTagName("span");
    }

    @Test
    public void testHasTagNameNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#id")).hasTagName("wrong"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The element does not have tag: wrong. Actual tag found : span");
    }

}
