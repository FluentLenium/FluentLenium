package org.fluentlenium.assertj.integration.list;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

public class FluentListHasAttributeValueTest extends IntegrationTest {

    @Test
    public void testHasAttributeValuePositive() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$(".small")).hasAttributeValue("name", "name");
    }

    @Test
    public void testHasAttributeValueNegative() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.$(".small")).hasAttributeValue("name", "name3"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have attribute name with value");
    }
}
