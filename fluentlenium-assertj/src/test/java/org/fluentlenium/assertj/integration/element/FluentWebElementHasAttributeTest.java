package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.fluentlenium.assertj.AssertionTestSupport.assertThatAssertionErrorIsThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;


/**
 * Integration test for {@link org.fluentlenium.assertj.custom.FluentWebElementAssert}.
 */
public class FluentWebElementHasAttributeTest extends IntegrationTest {

    @Test
    public void shouldHaveAttribute() {
        goTo(DEFAULT_URL);
        assertThat(el("button")).hasAttribute("id").isEqualTo("multiple-css-class");
    }

    @Test
    public void shouldFailWhenDoesNotHaveAttribute() {
        goTo(DEFAULT_URL);
        assertThatAssertionErrorIsThrownBy(() -> assertThat(el("select")).hasAttribute("class"))
                .hasMessage("The element does not have attribute class");
    }

    @Test
    public void shouldNotHaveAttribute() {
        goTo(DEFAULT_URL);
        assertThat(el("select")).hasNotAttribute("class");
    }

    @Test
    public void shouldFailWhenHasAttribute() {
        goTo(DEFAULT_URL);

        assertThatAssertionErrorIsThrownBy(() -> assertThat(el("select")).hasNotAttribute("id"))
                .hasMessage("The element has attribute id");
    }
}
