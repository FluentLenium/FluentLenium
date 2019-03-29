package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasValueTest extends IntegrationTest {

    @Test
    public void testHasValuePositive() {
        goTo(DEFAULT_URL);
        assertThat(el("#selected")).hasValue("John");
    }

    @Test
    public void testHasPropertyValueNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#selected")).hasValue("Jon"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The element does not have the value: Jon. Actual value found : John");
    }

}
