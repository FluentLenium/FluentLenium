package io.fluentlenium.assertj.integration.list;

import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentListHasNameTest extends IntegrationTest {

    @Test
    public void testHasNamePositive() {
        goTo(DEFAULT_URL);
        assertThat($(".small")).hasName("name2");
    }

    @Test
    public void testHasNameNegative() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat($(".small")).hasName("name3"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("No selected elements have name: name3");
    }
}
