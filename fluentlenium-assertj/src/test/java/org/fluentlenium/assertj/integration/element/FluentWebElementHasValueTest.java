package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasValueTest extends IntegrationTest {

    @Test
    public void testHasValuePositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#selected")).hasValue("John");
    }

    @Test
    public void testHasPropertyValueNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#selected")).hasValue("Jon"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The element does not have the value: Jon. Actual value found : John");
    }

}
