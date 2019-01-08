package org.fluentlenium.assertj.integration.list;

import org.fluentlenium.assertj.integration.IntegrationTest;
import org.junit.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentListAssertTest extends IntegrationTest {

    @Test
    public void testHasTextOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasText("A single line of text");
    }

    @Test(expected = AssertionError.class)
    public void testHasTextKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasText("John");
    }

    @Test
    public void testHasNotTextOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasNotText("John");
    }

    @Test(expected = AssertionError.class)
    public void testHasNotTextKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasNotText("Paul");
    }

    @Test
    public void testHasSizeOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize(9);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize(10);
    }

    @Test
    public void testHasSizeLessThanOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().lessThan(10);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().lessThan(9);
    }

    @Test
    public void testHasSizeLessThanOrEqualToOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().lessThanOrEqualTo(9);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanOrEqualToKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().lessThanOrEqualTo(8);
    }

    @Test
    public void testHasSizeGreaterThanOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().greaterThan(8);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().greaterThan(10);
    }

    @Test
    public void testHasSizeGreaterThanOrEqualToOk() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().greaterThanOrEqualTo(8);
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanOrEqualToKo() {
        standalone.goTo(DEFAULT_URL);
        assertThat(standalone.$("span")).hasSize().greaterThanOrEqualTo(10);
    }

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
