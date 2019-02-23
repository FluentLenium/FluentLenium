package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasNameTest extends IntegrationTest {

    @Test
    public void testHasNamePositive() {
        goTo(DEFAULT_URL);
        assertThat(el("body")).hasName("bodyName");
    }

    @Test
    public void testHasNameNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("body")).hasName("Jon"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The element does not have the name: Jon. Actual name found : bodyName");
    }

}
