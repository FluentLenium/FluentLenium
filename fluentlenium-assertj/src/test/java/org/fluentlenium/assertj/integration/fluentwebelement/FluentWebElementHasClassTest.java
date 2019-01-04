package org.fluentlenium.assertj.integration.fluentwebelement;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasClassTest extends IntegrationTest {

    @Test
    public void testAssertOnOneOfManyClasses() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.el("#multiple-css-class")).hasClass("class1");
    }

    @Test
    public void testAssertOnSubstringOfAClass() {
        standalone.goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(standalone.el("#multiple-css-class")).hasClass("wrongclass"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(
                        "The element does not have the class: wrongclass. Actual class found"
                                + " : class1 class2 class3");
    }

}
