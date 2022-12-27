package io.fluentlenium.assertj.integration.element;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentWebElementHasAttributeValueTest extends IntegrationTest {

    @Test
    public void testHasAttributeValuePositive() {
        goTo(DEFAULT_URL);
        assertThat(el(".textclass")).hasAttributeValue("id", "oneline");
    }

    @Test
    public void testHasAttributeValueNotPresent() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el(".textclass"))
                .hasAttributeValue("lorem", "ipsum"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The element does not have attribute lorem");
    }

    @Test
    public void testHasAttributeValueWrongValue() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el(".textclass"))
                .hasAttributeValue("id", "ipsum"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The id attribute does not have the value: ipsum. Actual value : oneline");
    }
}
