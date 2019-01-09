package org.fluentlenium.assertj.integration.list;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListHasClassTest extends IntegrationTest {

    @Test
    public void testAssertOnOneOfManyClasses() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("#multiple-css-class")).hasClass("class1");
    }

    @Test(expected = AssertionError.class)
    public void testAssertOnSubstringOfAClass() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("#multiple-css-class")).hasClass("cla");
    }
}
