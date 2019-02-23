package org.fluentlenium.assertj.integration.list;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

public class FluentListHasAttributeValueTest extends IntegrationTest {

    @Test
    public void testHasAttributeValuePositive() {
        goTo(DEFAULT_URL);
        assertThat($(".small")).hasAttributeValue("name", "name");
    }

    @Test
    public void testHasAttributeValueNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($(".small")).hasAttributeValue("name", "name3"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have attribute name with value");
    }
}
