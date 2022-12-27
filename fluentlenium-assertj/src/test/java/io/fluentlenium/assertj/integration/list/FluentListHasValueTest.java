package io.fluentlenium.assertj.integration.list;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

public class FluentListHasValueTest extends IntegrationTest {

    @Test
    public void testHasIdPositive() {
        goTo(DEFAULT_URL);
        assertThat($("[type=checkbox]")).hasValue("John");
    }

    @Test
    public void testHasIdNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($("[type=checkbox]")).hasValue("Johnny"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have value: Johnny");
    }
}
