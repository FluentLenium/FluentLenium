package io.fluentlenium.assertj.integration.list;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
