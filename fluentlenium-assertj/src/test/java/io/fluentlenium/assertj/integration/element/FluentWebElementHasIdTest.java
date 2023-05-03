package io.fluentlenium.assertj.integration.element;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentWebElementHasIdTest extends IntegrationTest {

    @Test
    public void testHasIdPositive() {
        goTo(DEFAULT_URL);
        assertThat(el(".textclass")).hasId("oneline");
    }

    @Test
    public void testHasIdNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el(".textclass")).hasId("wrongid"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("The element does not have the id: wrongid. Actual id found : oneline");
    }

}
