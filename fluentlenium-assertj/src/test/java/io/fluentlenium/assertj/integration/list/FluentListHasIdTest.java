package io.fluentlenium.assertj.integration.list;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

public class FluentListHasIdTest extends IntegrationTest {

    @Test
    public void testHasIdPositive() {
        goTo(DEFAULT_URL);
        assertThat($(".small")).hasId("id2");
    }

    @Test
    public void testHasIdNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($(".small")).hasId("id3"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have id: id3");
    }
}
