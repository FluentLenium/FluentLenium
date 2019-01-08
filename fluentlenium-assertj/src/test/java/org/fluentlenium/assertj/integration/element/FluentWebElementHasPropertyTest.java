package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasPropertyTest extends IntegrationTest {

    @Test
    public void testHasAttributeValuePositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el(".textclass")).hasAttributeValue("id", "oneline");
    }

    @Test
    public void testHasAttributeValuePropertyNotPresent() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el(".textclass"))
                .hasAttributeValue("lorem", "ipsum"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The element does not have attribute lorem");
    }

    @Test
    public void testHasAttributeValueWrongValue() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el(".textclass"))
                .hasAttributeValue("id", "ipsum"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The id attribute does not have the value: ipsum. Actual value : oneline");
    }
}
