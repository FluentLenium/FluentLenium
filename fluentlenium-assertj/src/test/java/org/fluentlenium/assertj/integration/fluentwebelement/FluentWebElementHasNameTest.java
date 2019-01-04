package org.fluentlenium.assertj.integration.fluentwebelement;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasNameTest extends IntegrationTest {

    @Test
    public void testHasNamePositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("body")).hasName("bodyName");
    }

    @Test
    public void testHasNameNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("body")).hasName("Jon"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The element does not have the name: Jon. Actual name found : bodyName");
    }

}
