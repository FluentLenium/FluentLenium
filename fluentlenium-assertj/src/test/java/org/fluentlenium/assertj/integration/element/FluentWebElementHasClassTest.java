package org.fluentlenium.assertj.integration.element;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementHasClassTest extends IntegrationTest {

    @Test
    public void testAssertOnOneOfManyClasses() {
        goTo(DEFAULT_URL);
        assertThat(el("#multiple-css-class")).hasClass("class1");
    }

    @Test
    public void testAssertOnSubstringOfAClass() {
        goTo(DEFAULT_URL);
        assertThatThrownBy(() -> assertThat(el("#multiple-css-class")).hasClass("wrongclass"))
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "The element does not have the class: wrongclass. Actual class found"
                                + " : class1 class2 class3");
    }

}
