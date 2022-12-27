package io.fluentlenium.assertj.integration.list;

import io.fluentlenium.assertj.AssertionTestSupport;
import io.fluentlenium.assertj.custom.FluentListAssert;
import io.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

/**
 * Integration test for{@link FluentListAssert}.
 */
public class FluentListHasAttributeTest extends IntegrationTest {

    @Test
    public void shouldHaveAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("input")).hasAttribute("type").contains("checkbox");
    }

    @Test
    public void shouldFailWhenNoElementHasAttribute() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(() -> assertThat($("input")).hasAttribute("data-type"))
                .hasMessage("No selected element has attribute data-type");
    }

    @Test
    public void shouldNotHaveAttribute() {
        goTo(DEFAULT_URL);
        assertThat($("input")).hasNotAttribute("data-type");
    }

    @Test
    public void shouldFailWhenAtLeastOneElementHasAttribute() {
        goTo(DEFAULT_URL);
        AssertionTestSupport.assertThatAssertionErrorIsThrownBy(() -> assertThat($("input")).hasNotAttribute("style"))
                .hasMessage("At least one selected element has attribute style");
    }
}
